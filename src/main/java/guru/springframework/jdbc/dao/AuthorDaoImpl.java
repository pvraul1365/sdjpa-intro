package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * AuthorDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 14/09/2026 - 18:18
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Author getById(final Long id) {
        final String query = "select author.id, author.first_name, author.last_name, book.id, book.title, book.isbn, book.publisher, book.author_id " +
                "from author left outer join book on author.id = book.author_id where author.id = ?";

        // return this.jdbcTemplate.queryForObject("select * from author where id = ?", new Object[] {id}, getRowMapper());
        return this.jdbcTemplate.query(query, new Object[]{id}, rs -> {
            Author author = null;

            while (rs.next()) {
                if (author == null) {
                    author = new Author();
                    author.setId(rs.getLong("id"));
                    author.setFirstName(rs.getString("first_name"));
                    author.setLastName(rs.getString("last_name"));
                    author.setBooks(new ArrayList<>());
                }
                // You can also map the book information here if needed
                if (rs.getLong("book.id") != 0) {
                    // Map book information if needed
                    // For example, you can create a Book object and add it to the author's book list
                    Book book = new Book();
                    book.setId(rs.getLong("book.id"));
                    book.setTitle(rs.getString("book.title"));
                    book.setIsbn(rs.getString("book.isbn"));
                    book.setPublisher(rs.getString("book.publisher"));
                    book.setAuthorId(rs.getLong("book.author_id"));
                    // Add the book to the author's book list if you have a list in the Author class
                    if (author.getBooks() != null) {
                        author.getBooks().add(book);
                    }
                }
            }
            if (author == null) {
                throw new EmptyResultDataAccessException(1);
            }
            return author;
        });
    }

    @Override
    public Author findAuthorByName(final String firstName, final String lastName) {

        return this.jdbcTemplate.queryForObject("select * from author where first_name = ? and last_name = ?",
                new Object[] {firstName, lastName},
                getRowMapper());
    }

    @Override
    public Author saveNewAuthor(final Author author) {
        this.jdbcTemplate.update("insert into author (first_name, last_name) values (?, ?)",
                author.getFirstName(), author.getLastName());

        final Long createdId = this.jdbcTemplate.queryForObject("select last_insert_id()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Author updateAuthor(final Author author) {
        this.jdbcTemplate.update("update author set first_name = ?, last_name = ? where id = ?",
                author.getFirstName(), author.getLastName(), author.getId());

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(final Long id) {
        this.jdbcTemplate.update("delete from author where id = ?", id);
    }

    private RowMapper<Author> getRowMapper() {
        return new AuthorMapper();
    }

}
