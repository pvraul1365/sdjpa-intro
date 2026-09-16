package guru.springframework.sdjpa_intro.dao;

import guru.springframework.sdjpa_intro.domain.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                return getAuthorFromResultSet(resultSet);
            }
        } catch (Exception e) {
            log.error("Error fetching author by id: {}", id, e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Author findAuthorByName(final String firstName, final String lastName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getAuthorFromResultSet(resultSet);
            }

        } catch (Exception e) {
            log.error("Error fetching author by name: {} {}", firstName, lastName, e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Author saveNewAuthor(final Author author) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO author (first_name, last_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                author.setId(resultSet.getLong(1));
                return author;
            } else {
                throw new SQLException("Creating author failed, no ID obtained.");
            }
        } catch (Exception e) {
            log.error("Error saving new author: {}", author, e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }
        return null;
    }

    @Override
    public Author updateAuthor(final Author author) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?");
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating author failed, no rows affected.");
            }

            return author;
        } catch (Exception e) {
            log.error("Error updating author: {}", author, e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    private void closeAllResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try { if (resultSet != null) resultSet.close(); } catch (Exception e) { log.error("Error closing ResultSet", e); }
        try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) { log.error("Error closing PreparedStatement", e); }
        try { if (connection != null) connection.close(); } catch (Exception e) { log.error("Error closing Connection", e); }
    }

    private Author getAuthorFromResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }
}
