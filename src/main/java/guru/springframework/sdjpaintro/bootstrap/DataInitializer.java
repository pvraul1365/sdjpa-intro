package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * DataInitializer
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 07/09/2026 - 19:46
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("DataInitializer is running...");

        // Add your data initialization logic here
        Book bookDDD = new Book(
                "Domain Driven Design",
                "978-0321125217",
                "Addison-Wesley Professional"
        );

        Book savedDDD = this.bookRepository.save(bookDDD);
        log.info("ID DataInitializer savedDDD: {}", savedDDD.getId());

        Book bookSIA = new Book(
                "Spring in Action",
                "978-1617294945",
                "Manning Publications"
        );

        Book savedSIA = this.bookRepository.save(bookSIA);
        log.info("ID DataInitializer savedSIA: {}", savedSIA.getId());

        this.bookRepository.findAll().forEach(book -> {
            log.info("Book: {} - {} - {}", book.getTitle(), book.getIsbn(), book.getPublisher());
        });
    }
}
