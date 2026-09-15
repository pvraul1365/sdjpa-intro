package guru.springframework.sdjpa_intro.dao;

import guru.springframework.sdjpa_intro.domain.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource dataSource;

    @Override
    public Author getById(final Long id) {
        Connection connection = null;
        // Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            // statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            // resultSet = statement.executeQuery("SELECT * FROM author WHERE id = " + id);

            if (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));

                return author;
            }
        } catch (Exception e) {
            log.error("Error fetching author by id: {}", id, e);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (Exception e) { log.error("Error closing ResultSet", e); }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) { log.error("Error closing PreparedStatement", e); }
            try { if (connection != null) connection.close(); } catch (Exception e) { log.error("Error closing Connection", e); }
        }

        return null;
    }

}
