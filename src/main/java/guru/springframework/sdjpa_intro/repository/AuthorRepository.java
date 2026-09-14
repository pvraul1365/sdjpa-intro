package guru.springframework.sdjpa_intro.repository;

import guru.springframework.sdjpa_intro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 14/09/2026 - 17:46
 * @since 1.25
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
