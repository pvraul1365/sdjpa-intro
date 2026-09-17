package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.RowMapper;

/**
 * AuthorMapper
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 17/09/2026 - 14:05
 * @since 1.25
 */
public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Author author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        return author;
    }

}
