package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * BookDaoIntegrationTest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 19/09/2026 - 18:34
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
@Import({BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BookDaoIntegrationTest {

    @Autowired
    BookDaoImpl bookDao;

    @Test
    void testFindAllBooksSortedByTitle() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").descending());
        var books = bookDao.findAllBooksSortedByTitle(pageable);

        assert books != null;
        assert books.size() == 10;

        log.info("Books: {}", books);
    }

    @Test
    void testFindAllBooksPageable() {
        Pageable pageable = PageRequest.of(0, 10);
        var books = bookDao.findAllBooks(pageable);
        assert books != null;
        assert books.size() == 10;

        log.info("Books: {}", books);
    }

    @Test
    void testFindAllBooks() {
        var books = bookDao.findAllBooks(0, 10);
        assert books != null;
        assert books.size() == 10;

        log.info("Books: {}", books);
    }

    @Test
    void testGetBook() {
        Long bookId = 1L; // Replace with a valid book ID from your database
        var book = bookDao.getById(bookId);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testGetBookByTitle() {
        String title = "Spring in Action, 5th Edition"; // Replace with a valid book title from your database
        var book = bookDao.findBookByTitle(title);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testSaveNewBook() {
        var newBook = new guru.springframework.jdbc.domain.Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("New Publisher");
        newBook.setAuthorId(1L); // Replace with a valid author ID from your database

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        log.info("Saved Book: {}", savedBook);
    }

    @Test
    void testUpdateBook() {
        var newBook = new guru.springframework.jdbc.domain.Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("New Publisher");
        newBook.setAuthorId(1L); // Replace with a valid author ID from your database
        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        savedBook.setTitle("Updated Book Title");
        var updatedBook = bookDao.updateBook(savedBook);
        assert updatedBook != null;
        assert  updatedBook.getTitle().equals("Updated Book Title");

        log.info("Updated Book: {}", updatedBook);
    }

    @Test
    void testDeleteBook() {
        var newBook = new guru.springframework.jdbc.domain.Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("New Publisher");
        newBook.setAuthorId(1L); // Replace with a valid author ID from your database
        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        bookDao.deleteBookById(savedBook.getId());

        assertThatThrownBy(() -> bookDao.getById(savedBook.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        log.info("Deleted Book: {}", savedBook);
    }

}
