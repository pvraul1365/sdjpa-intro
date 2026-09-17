package guru.springframework.sdjpa_intro;

import guru.springframework.sdjpa_intro.dao.AuthorDao;
import guru.springframework.sdjpa_intro.dao.AuthorDaoImpl;
import guru.springframework.sdjpa_intro.dao.BookDao;
import guru.springframework.sdjpa_intro.dao.BookDaoImpl;
import guru.springframework.sdjpa_intro.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * BookDaoIntegrationTest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 17/09/2026 - 10:45
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
@Import({ BookDaoImpl.class, AuthorDaoImpl.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetBook() {
        Long bookId = 1L; // Replace with a valid book ID from your database
        var book = bookDao.getById(bookId);
        assert book != null;

        log.info("Book: " + book);
    }

    @Test
    void testGetBookByTitle() {
        String title = "Spring in Action, 5th Edition";
        var book = bookDao.findBookByTitle(title);
        assert book != null;

        log.info("Book: " + book);
    }

    @Test
    void testSaveBook() {
        var newBook = new Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("Test Publisher");

        var author = authorDao.getById(1L); // Assuming author with ID 1 exists
        newBook.setAuthor(author);

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        log.info("Saved Book: " + savedBook);
    }

    @Test
    void testUpdateBook() {
        var newBook = new Book();
        newBook.setTitle("Another New Book Title");
        newBook.setIsbn("0987654321");
        newBook.setPublisher("Another Test Publisher");

        var author = authorDao.getById(1L); // Assuming author with ID 1 exists
        newBook.setAuthor(author);

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        String updatedTitle = "Updated Book Title";
        savedBook.setTitle(updatedTitle);
        var updatedBook = bookDao.updateBook(savedBook);
        assert updatedBook != null;
        assert updatedBook.getTitle().equals(updatedTitle);

        log.info("Updated Book: " + updatedBook);
    }

    @Test
    void testDeleteBook() {
        var newBook = new Book();
        newBook.setTitle("Book to Delete");
        newBook.setIsbn("1122334455");
        newBook.setPublisher("Delete Test Publisher");
        var author = authorDao.getById(1L); // Assuming author with ID 1 exists
        newBook.setAuthor(author);

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        bookDao.deleteBookById(savedBook.getId());

        var deletedBook = bookDao.getById(savedBook.getId());
        assert deletedBook == null;

        log.info("Deleted Book: " + savedBook);
    }

}
