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

        log.info("Author: {}", author);
    }

    @Test
    void testFindAuthorByName() {
        String firstName = "Craig"; // Replace with a valid first name from your database
        String lastName = "Walls"; // Replace with a valid last name from your database
        var author = authorDao.findAuthorByName(firstName, lastName);
        assert author != null;

        log.info("Author: {}", author);
    }

    @Test
    void testSaveNewAuthor() {
        var newAuthor = new Author();
        newAuthor.setFirstName("Marta");
        newAuthor.setLastName("Mazo");

        var savedAuthor = authorDao.saveNewAuthor(newAuthor);
        assert savedAuthor != null;

        log.info("Saved Author: {}", savedAuthor);
    }

    @Test
    void testUpdateAuthor() {
        var newAuthor = new Author();
        newAuthor.setFirstName("Marta");
        newAuthor.setLastName("Mazo");

        var savedAuthor = authorDao.saveNewAuthor(newAuthor);

        savedAuthor.setFirstName("UpdatedFirstName");
        savedAuthor.setLastName("UpdatedLastName");

        var updatedAuthor = authorDao.updateAuthor(savedAuthor);
        assert updatedAuthor != null;
        assert updatedAuthor.getFirstName().equals("UpdatedFirstName");
        assert updatedAuthor.getLastName().equals("UpdatedLastName");

        log.info("Updated Author: {}", updatedAuthor);
    }

    @Test
    void testDeleteAuthor() {
        var newAuthor = new Author();
        newAuthor.setFirstName("Marta");
        newAuthor.setLastName("Mazo");

        var savedAuthor = authorDao.saveNewAuthor(newAuthor);
        Long authorId = savedAuthor.getId();

        authorDao.deleteAuthorById(authorId);

        var deletedAuthor = authorDao.getById(authorId);
        assert deletedAuthor == null;

        log.info("Deleted Author with ID: {}", authorId);
    }

}
