package guru.springframework.sdjpaintro.domain.composite;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * NamedId
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 13/09/2026 - 20:28
 * @since 1.25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NamedId implements Serializable {

    private String firstName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NamedId namedId = (NamedId) o;
        return Objects.equals(firstName, namedId.firstName) && Objects.equals(lastName, namedId.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
