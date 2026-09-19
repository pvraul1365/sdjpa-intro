package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.domain.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * AuthorDaoIntegrationTest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 14/09/2026 - 18:19
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
@Import({AuthorDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {
        Long authorId = 1L; // Replace with a valid author ID from your database
        var author = authorDao.getById(authorId);
        assert author != null;

        log.info("Author: " + author);
    }

    @Test
    void testGetAuthorByName() {
        String firstName = "Craig"; // Replace with a valid first name from your database
        String lastName = "Walls"; // Replace with a valid last name from your database
        var author = authorDao.findAuthorByName(firstName, lastName);
        assert author != null;

        log.info("Author: " + author);
    }

    @Test
    void testSaveAuthor() {
        var newAuthor = new Author();
        newAuthor.setFirstName("John");
        newAuthor.setLastName("Doe");

        var savedAuthor = authorDao.saveNewAuthor(newAuthor);
        assert savedAuthor != null;
        assert savedAuthor.getId() != null;

        log.info("Saved Author: " + savedAuthor);
    }

    @Test
    void testUpdateAuthor() {
        Long authorId = 24L; // Replace with a valid author ID from your database
        var author = authorDao.getById(authorId);
        assert author != null;

        String updatedFirstName = "UpdatedFirstName";
        String updatedLastName = "UpdatedLastName";
        author.setFirstName(updatedFirstName);
        author.setLastName(updatedLastName);

        var updatedAuthor = authorDao.updateAuthor(author);
        assert updatedAuthor != null;
        assert updatedAuthor.getFirstName().equals(updatedFirstName);
        assert updatedAuthor.getLastName().equals(updatedLastName);

        log.info("Updated Author: " + updatedAuthor);
    }

    @Test
    void testDeleteAuthor() {
        var newAuthor = new Author();
        newAuthor.setFirstName("John");
        newAuthor.setLastName("Doe");

        var savedAuthor = authorDao.saveNewAuthor(newAuthor);
        var author = authorDao.getById(savedAuthor.getId());
        assert author != null;

        authorDao.deleteAuthorById(savedAuthor.getId());

        var deletedAuthor = authorDao.getById(savedAuthor.getId());
        assert deletedAuthor == null;

        log.info("Deleted Author with ID: {}", savedAuthor.getId());
    }

    @Test
    void testListAuthorByLastNameLike() {
        String lastNamePattern = "Walls"; // Replace with a valid last name pattern from your database
        var authors = authorDao.listAuthorByLastNameLike(lastNamePattern);
        assert authors != null && !authors.isEmpty();

        log.info("Authors with last name like '{}': {}", lastNamePattern, authors);
    }

    @Test
    void testFindAllAuthors() {
        var authors = authorDao.findAllAuthors();
        assert authors != null && !authors.isEmpty();

        log.info("All Authors: {}", authors);
    }

}
