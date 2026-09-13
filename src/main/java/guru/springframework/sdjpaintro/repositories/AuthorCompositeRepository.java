package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.Author;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.NamedId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorCompositeRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 13/09/2026 - 20:31
 * @since 1.25
 */
public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NamedId> {
}
