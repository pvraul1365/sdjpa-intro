package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * BookDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 19/09/2026 - 18:27
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooksSortedByTitle(final Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.getContent();
    }

    @Override
    public List<Book> findAllBooks(final Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.getContent();
    }

    @Override
    public List<Book> findAllBooks(final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.getContent();
    }

    @Override
    public Book getById(final Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    @Override
    public Book findBookByTitle(final String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
    }

    @Override
    public Book saveNewBook(final Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(final Book book) {
        var foundBook = bookRepository.findById(book.getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (foundBook != null) {
            foundBook.setTitle(book.getTitle());
            foundBook.setIsbn(book.getIsbn());
            foundBook.setPublisher(book.getPublisher());
            foundBook.setAuthorId(book.getAuthorId());

            return bookRepository.save(foundBook);
        }

        return null;
    }

    @Override
    public void deleteBookById(final Long id) {
        bookRepository.deleteById(id);
    }
}
