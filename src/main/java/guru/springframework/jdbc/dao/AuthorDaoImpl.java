package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Author getById(Long id) {
        return this.getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = this.getEntityManager().createQuery(
                "SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);

        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = this.getEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = this.getEntityManager();

        entityManager.getTransaction().begin();
        Author updatedAuthor = entityManager.merge(author);
        entityManager.getTransaction().commit();

        return updatedAuthor;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager entityManager = this.getEntityManager();

        entityManager.getTransaction().begin();
        Author authorToDelete = entityManager.find(Author.class, id);
        if (authorToDelete != null) {
            entityManager.remove(authorToDelete);
        }
        entityManager.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
