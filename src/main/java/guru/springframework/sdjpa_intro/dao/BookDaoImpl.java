package guru.springframework.sdjpa_intro.dao;

import guru.springframework.sdjpa_intro.domain.Author;
import guru.springframework.sdjpa_intro.domain.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BookDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 17/09/2026 - 09:41
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {

    private final DataSource dataSource;

    @Override
    public Book getById(final Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE id = ?");
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error fetching book by id", e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Book findBookByTitle(final String title) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM book WHERE title = ?");
            preparedStatement.setString(1, title);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getBookFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            log.error("Error fetching book by title", e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Book saveNewBook(final Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                "INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setLong(4, book.getAuthorId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                book.setId(resultSet.getLong(1));
            } else {
                throw new SQLException("Creating book failed, no ID obtained.");
            }

            return book;
        } catch (SQLException e) {
            log.error("Error saving new book", e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public Book updateBook(final Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                "UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?"
            );
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setLong(4, book.getAuthorId());
            preparedStatement.setLong(5, book.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }

            return book;

        } catch (SQLException e) {
            log.error("Error updating book", e);
        } finally {
            closeAllResources(connection, preparedStatement, resultSet);
        }

        return null;
    }

    @Override
    public void deleteBookById(final Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting book failed, no rows affected.");
            }

        } catch (SQLException e) {
            log.error("Error deleting book by id", e);
        } finally {
            closeAllResources(connection, preparedStatement, null);
        }
    }

    private void closeAllResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try { if (resultSet != null) resultSet.close(); } catch (Exception e) { log.error("Error closing ResultSet", e); }
        try { if (preparedStatement != null) preparedStatement.close(); } catch (Exception e) { log.error("Error closing PreparedStatement", e); }
        try { if (connection != null) connection.close(); } catch (Exception e) { log.error("Error closing Connection", e); }
    }

    private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setAuthorId(resultSet.getLong("author_id"));

        return book;
    }
}
