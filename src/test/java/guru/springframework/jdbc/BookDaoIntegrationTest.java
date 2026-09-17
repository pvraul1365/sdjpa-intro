package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.AuthorDao;
import guru.springframework.jdbc.dao.AuthorDaoImpl;
import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.BookDaoImpl;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * BookDaoIntegrationTest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 17/09/2026 - 16:21
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BookDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Test
    void testGetBook() {
        Long bookId = 1L; // Replace with a valid book ID from your database
        var book = bookDao.getById(bookId);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testFindBookByTitle() {
        String title = "Spring in Action, 5th Edition"; // Replace with a valid book title from your database
        var book = bookDao.findBookByTitle(title);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testSaveNewBook() {
        // First, create a new author to associate with the book
        var newAuthor = new Author();
        newAuthor.setFirstName("Jane");
        newAuthor.setLastName("Smith");
        var savedAuthor = authorDao.saveNewAuthor(newAuthor);

        // Now, create a new book associated with the saved author
        var newBook = new Book();
        newBook.setTitle("Spring Boot in Action");
        newBook.setIsbn("9781617292545");
        newBook.setPublisher("Manning Publications");
        newBook.setAuthorId(savedAuthor.getId());

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        log.info("Saved Book: {}", savedBook);
    }

    @Test
    void testUpdateBook() {
        // First, create a new author to associate with the book
        var newAuthor = new Author();
        newAuthor.setFirstName("John");
        newAuthor.setLastName("Doe");
        var savedAuthor = authorDao.saveNewAuthor(newAuthor);

        // Now, create a new book associated with the saved author
        var newBook = new Book();
        newBook.setTitle("Spring Boot in Action");
        newBook.setIsbn("9781617292545");
        newBook.setPublisher("Manning Publications");
        newBook.setAuthorId(savedAuthor.getId());

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        // Update the book's title
        String updatedTitle = "Spring Boot in Action - Updated";
        savedBook.setTitle(updatedTitle);
        var updatedBook = bookDao.updateBook(savedBook);

        assert updatedBook != null;
        assert updatedBook.getTitle().equals(updatedTitle);

        log.info("Updated Book: {}", updatedBook);
    }

    @Test
    void testDeleteBook() {
        // First, create a new author to associate with the book
        var newAuthor = new Author();
        newAuthor.setFirstName("John");
        newAuthor.setLastName("Doe");
        var savedAuthor = authorDao.saveNewAuthor(newAuthor);

        // Now, create a new book associated with the saved author
        var newBook = new Book();
        newBook.setTitle("Spring Boot in Action");
        newBook.setIsbn("9781617292545");
        newBook.setPublisher("Manning Publications");
        newBook.setAuthorId(savedAuthor.getId());

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;

        // Delete the book
        bookDao.deleteBookById(savedBook.getId());

        // Try to retrieve the deleted book
        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getById(savedBook.getId());
        });
    }

}
