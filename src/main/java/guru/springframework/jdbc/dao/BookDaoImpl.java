package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * BookDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 17/09/2026 - 16:16
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY id ASC", new BookMapper());
    }

    @Override
    public Book getById(final Long id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?",
                getRowMapper(), id);
    }

    @Override
    public Book findBookByTitle(final String title) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?",
                getRowMapper(), title);
    }

    @Override
    public Book saveNewBook(final Book book) {
        this.jdbcTemplate.update("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());
        final Long createdId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Book updateBook(final Book book) {
        this.jdbcTemplate.update("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(final Long id) {
        this.jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    private RowMapper<Book> getRowMapper() {
        return new BookMapper();
    }
}
