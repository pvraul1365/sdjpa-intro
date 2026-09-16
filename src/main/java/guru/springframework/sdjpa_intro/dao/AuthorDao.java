package guru.springframework.sdjpa_intro.dao;

import guru.springframework.sdjpa_intro.domain.Author;

/**
 * AuthorDao
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 14/09/2026 - 18:16
 * @since 1.25
 */
public interface AuthorDao {

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthor(Long id);
}
