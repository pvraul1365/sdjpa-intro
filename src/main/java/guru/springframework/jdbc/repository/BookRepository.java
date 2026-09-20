package guru.springframework.jdbc.repository;

import guru.springframework.jdbc.domain.Book;
import jakarta.annotation.Nullable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 14/09/2026 - 17:47
 * @since 1.25
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    Book readByTitle(String title);

    @Nullable
    Book getByTitle(@Nullable String title);
}
