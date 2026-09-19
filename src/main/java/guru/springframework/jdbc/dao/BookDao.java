package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import java.util.List;

/**
 * BookDao
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 19/09/2026 - 09:02
 * @since 1.25
 */
public interface BookDao {

    List<Book> findAllBooks();

    Book findByIsbn(String isbn);

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
