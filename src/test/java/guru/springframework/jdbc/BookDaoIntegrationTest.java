package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDao;
import guru.springframework.jdbc.dao.BookDaoImpl;
import guru.springframework.jdbc.domain.Book;
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
 * @version 19/09/2026 - 09:36
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
@Import({ BookDaoImpl.class })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testFindBookByTitleCriteria() {
        String title = "Spring in Action, 5th Edition"; // Replace with a valid title from your database
        var book = bookDao.findBookByTitleCriteria(title);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testFindBookByTitleNative() {
        String title = "Spring in Action, 5th Edition"; // Replace with a valid title from your database
        var book = bookDao.findBookByTitleNative(title);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testFindAllBooks() {
        var book = bookDao.findAllBooks();
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testFindByIsbn() {
        String isbn = "978-1617294945"; // Replace with a valid ISBN from your database
        var book = bookDao.findByIsbn(isbn);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testGetBook() {
        Long bookId = 1L; // Replace with a valid book ID from your database
        var book = bookDao.getById(bookId);
        assert book != null;

        log.info("Book: {}", book);
    }

    @Test
    void testGetBookByTitle() {
        String title = "Spring in Action, 5th Edition"; // Replace with a valid title from your database
        var book = bookDao.findBookByTitle(title);
        assert book != null;
    }

    @Test
    void testSaveBook() {
        var newBook = new Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("Test Publisher");
        newBook.setAuthorId(1L); // Replace with a valid author ID from your database

        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;
        assert savedBook.getId() != null;

        log.info("Saved Book: {}", savedBook);
    }

    @Test
    void testUpdateBook() {
        var newBook = new Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("Test Publisher");
        newBook.setAuthorId(1L);
        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;
        assert savedBook.getId() != null;

        savedBook.setTitle("Updated Book Title");

        var updatedBook = bookDao.updateBook(savedBook);
        assert updatedBook != null;
        assert updatedBook.getTitle().equals("Updated Book Title");

        log.info("Updated Book: {}", updatedBook);
    }

    @Test
    void testDeleteBook() {
        var newBook = new Book();
        newBook.setTitle("New Book Title");
        newBook.setIsbn("1234567890");
        newBook.setPublisher("Test Publisher");
        newBook.setAuthorId(1L);
        var savedBook = bookDao.saveNewBook(newBook);
        assert savedBook != null;
        assert savedBook.getId() != null;

        bookDao.deleteBookById(savedBook.getId());

        var deletedBook = bookDao.getById(savedBook.getId());
        assert deletedBook == null;

        log.info("Deleted Book with ID: {}", savedBook.getId());
    }

}
