package guru.springframework.sdjpaintro.domain.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * AuthorEmbedded
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 14/09/2026 - 09:44
 * @since 1.25
 */
@Entity
@Table(name = "author_composite")
@Getter
@Setter
@NoArgsConstructor
public class AuthorEmbedded {

    @EmbeddedId
    private NamedId nameId;

    private String country;

    public AuthorEmbedded(NamedId nameId) {
        this.nameId = nameId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEmbedded that = (AuthorEmbedded) o;
        return Objects.equals(nameId, that.nameId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameId);
    }
}
