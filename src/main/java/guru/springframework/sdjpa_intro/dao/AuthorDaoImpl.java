package guru.springframework.sdjpa_intro.dao;

import guru.springframework.sdjpa_intro.domain.Author;
import org.springframework.stereotype.Component;

/**
 * AuthorDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 14/09/2026 - 18:18
 * @since 1.25
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    @Override
    public Author getById(Long id) {
        return null;
    }

}
