package guru.springframework.sdjpaintro.repositories;

import guru.springframework.sdjpaintro.domain.BookUuid;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BookUuidRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 13/09/2026 - 14:49
 * @since 1.25
 */
public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
