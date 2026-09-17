package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import lombok.RequiredArgsConstructor;
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

        return this.jdbcTemplate.queryForObject("select * from author where id = ?", new Object[] {id},
                getRowMapper());
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
