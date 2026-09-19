package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import java.util.List;

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

    Author findAuthorByNameCriteria(String firstName, String lastName);

    List<Author> findAllAuthors();

    List<Author> listAuthorByLastNameLike(String lastName);

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

}
