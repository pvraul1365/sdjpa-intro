package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.BookNatural;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NamedId;
import guru.springframework.sdjpaintro.repositories.AuthorCompositeRepository;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookNaturalRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * MySQLIntegrationTest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 09/09/2026 - 08:11
 * @since 1.25
 */
@ActiveProfiles("local")
@DataJpaTest
//@ComponentScan(basePackages = {"guru.springframework.sdjpaintro.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Test
    void authorCompositeTest(){
        NamedId namedId = new NamedId("John", "Doe");
        var authorComposite = new AuthorComposite();
        authorComposite.setFirstName(namedId.getFirstName());
        authorComposite.setLastName(namedId.getLastName());
        authorComposite.setCountry("USA");

        var savedAuthorComposite = authorCompositeRepository.save(authorComposite);
        assertThat(savedAuthorComposite).isNotNull();

        var foundAuthorComposite = authorCompositeRepository.findById(namedId).orElse(null);
        assertThat(foundAuthorComposite).isNotNull();
    }

    @Test
    void bookNaturalTest() {
        BookNatural bookNatural = BookNatural.builder()
                .title("Book Title")
                .isbn("1234567890")
                .publisher("Publisher")
                .build();

        BookNatural savedBookNatural = bookNaturalRepository.save(bookNatural);
        log.info("ID MySQLIntegrationTest savedBookNatural: {}", savedBookNatural.getTitle());

        assertThat(savedBookNatural).isNotNull();
        assertThat(savedBookNatural.getTitle()).isNotNull();
    }

    @Test
    void testBookUuid() {
        BookUuid bookUuid = new BookUuid();
        BookUuid savedBookDDD = bookUuidRepository.save(bookUuid);
        log.info("ID MySQLIntegrationTest savedBookDDD: {}", savedBookDDD.getId());

        assertThat(savedBookDDD).isNotNull();
        assertThat(savedBookDDD.getId()).isNotNull();

        final BookUuid foundBook = bookUuidRepository.findById(savedBookDDD.getId()).orElse(null);
        assertThat(foundBook).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorUuid authorUuid = new AuthorUuid();
        AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
        log.info("ID MySQLIntegrationTest savedAuthor: {}", savedAuthor.getId());

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();

        final AuthorUuid foundAuthor = authorUuidRepository.findById(savedAuthor.getId()).orElse(null);
        assertThat(foundAuthor).isNotNull();
    }

    @Test
    void testMySQL() {
        var countBefore = bookRepository.count();

        assertThat(countBefore).isEqualTo(2);
    }
}
