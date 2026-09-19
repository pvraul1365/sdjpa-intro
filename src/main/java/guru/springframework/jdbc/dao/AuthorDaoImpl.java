package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    @Override
    public Author getById(final Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author findAuthorByName(final String firstName, final String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
    }

    @Override
    public Author saveNewAuthor(final Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(final Author author) {
        Author foundAuthor = authorRepository.findById(author.getId()).orElse(null);
        if (foundAuthor != null) {
            foundAuthor.setFirstName(author.getFirstName());
            foundAuthor.setLastName(author.getLastName());
        }
        return authorRepository.save(foundAuthor);
    }

    @Transactional
    @Override
    public void deleteAuthorById(final Long id) {
        authorRepository.deleteById(id);
    }
}
