package guru.springframework.sdjpaintro.domain.composite;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthorComposite
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 13/09/2026 - 20:26
 * @since 1.25
 */
@Entity
@IdClass(NamedId.class)
@Getter
@Setter
@NoArgsConstructor
public class AuthorComposite {

    @Id
    private String firstName;

    @Id
    private String lastName;

    private String country;

}
