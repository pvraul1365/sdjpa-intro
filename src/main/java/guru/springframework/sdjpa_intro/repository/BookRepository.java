package guru.springframework.sdjpa_intro.repository;

import guru.springframework.sdjpa_intro.domain.Book;
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
}
