package guru.springframework.sdjpa_intro;

import guru.springframework.sdjpa_intro.dao.AuthorDao;
import guru.springframework.sdjpa_intro.dao.AuthorDaoImpl;
import guru.springframework.sdjpa_intro.domain.Author;
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
        String firstName = "Robert"; // Replace with a valid first name from your database
        String lastName = "Martin"; // Replace with a valid last name from your database
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

        log.info("Saved Author: " + savedAuthor);
    }

}
