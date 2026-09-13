package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorUuidRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 13/09/2026 - 14:26
 * @since 1.25
 */
public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
}
