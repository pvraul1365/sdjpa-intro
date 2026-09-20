package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * BookDao
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 17/09/2026 - 16:15
 * @since 1.25
 */
public interface BookDao {

    List<Book> findAllBooks(Pageable pageable);

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks();

    Book getById(final Long id);

    Book findBookByTitle(final String title);

    Book saveNewBook(final Book book);

    Book updateBook(final Book book);

    void deleteBookById(final Long id);
}
