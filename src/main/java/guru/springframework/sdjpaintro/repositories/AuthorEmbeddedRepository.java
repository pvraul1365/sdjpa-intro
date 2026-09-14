package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.composite.AuthorEmbedded;
import guru.springframework.sdjpaintro.domain.composite.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorEmbeddedRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 14/09/2026 - 16:34
 * @since 1.25
 */
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NamedId> {
}
