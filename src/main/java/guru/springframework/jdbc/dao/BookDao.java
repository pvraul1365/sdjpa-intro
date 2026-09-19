package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

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

    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
