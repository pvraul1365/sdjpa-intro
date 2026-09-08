package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

/**
 * SpringBootJpaTestSlice
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 08/09/2026 - 18:27
 * @since 1.25
 */
@DataJpaTest
public class SpringBootJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testJpaTestSplice() {
        var countBefore = bookRepository.count();

        bookRepository.save(new Book(
                "Test Driven Development",
                "978-0321146533",
                "Addison-Wesley Professional"
        ));

        var countAfter = bookRepository.count();
        
        assert(countAfter > countBefore);
    }
}
