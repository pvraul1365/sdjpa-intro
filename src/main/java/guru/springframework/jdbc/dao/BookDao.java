package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;

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

    Book getById(final Long id);

    Book findBookByTitle(final String title);

    Book saveNewBook(final Book book);

    Book updateBook(final Book book);

    void deleteBookById(final Long id);
}
