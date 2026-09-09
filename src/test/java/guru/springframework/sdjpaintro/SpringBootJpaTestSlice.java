package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * SpringBootJpaTestSlice
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 08/09/2026 - 18:27
 * @since 1.25
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaTestSlice {

    @Autowired
    BookRepository bookRepository;

    @Order(1)
    //@Rollback(false)
    @Commit // This annotation will commit the transaction after the test method is executed, so the changes will be persisted in the database.
    @Test
    void testJpaTestSplice() {
        var countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);

        bookRepository.save(new Book(
                "Test Driven Development",
                "978-0321146533",
                "Addison-Wesley Professional"
        ));

        var countAfter = bookRepository.count();

        assert(countAfter > countBefore);
    }

    @Order(2)
    @Test
    void testJpaTestSpliceTransaction() {
        var countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);

    }
}
