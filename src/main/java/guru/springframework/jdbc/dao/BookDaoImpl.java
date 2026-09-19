package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BookDaoImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicente@gmail.com
 * @version 19/09/2026 - 09:13
 * @since 1.25
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book findBookByTitleCriteria(final String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

            Root<Book> bookRoot = criteriaQuery.from(Book.class);
            ParameterExpression<String> titleParameter = criteriaBuilder.parameter(String.class);
            Predicate titlePredicate = criteriaBuilder.equal(bookRoot.get("title"), titleParameter);
            criteriaQuery.select(bookRoot).where(titlePredicate);

            TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(titleParameter, title);

            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error finding book by title: {}", title, e);
            throw new RuntimeException("Error finding book by title: " + title, e);
        } finally {
            entityManager.close();
        }

    }

    @Override
    public List<Book> findAllBooks() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            TypedQuery<Book> query = entityManager.createNamedQuery(
                    "book_find_all", Book.class);

            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error finding all books", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Book findByIsbn(final String isbn) {
        EntityManager entityManager = this.getEntityManager();

        try {
            /*TypedQuery<Book> query = entityManager.createQuery(
                    "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);*/
            TypedQuery<Book> query = entityManager.createNamedQuery(
                    "book_find_by_isbn", Book.class);
            query.setParameter("isbn", isbn);

            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Error finding book by ISBN: " + isbn, e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Book getById(final Long id) {
        return this.getEntityManager().find(Book.class, id);
    }

    @Override
    public Book findBookByTitle(final String title) {
        EntityManager entityManager = this.getEntityManager();

        TypedQuery<Book> query = entityManager.createNamedQuery(
                "book_find_by_title", Book.class);
        query.setParameter("title", title);

        return query.getSingleResult();
    }

    @Override
    public Book saveNewBook(final Book book) {
        EntityManager entityManager = this.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();

        return book;
    }

    @Override
    public Book updateBook(final Book book) {
        EntityManager entityManager = this.getEntityManager();
        entityManager.getTransaction().begin();
        Book updatedBook = entityManager.merge(book);
        entityManager.getTransaction().commit();

        return updatedBook;
    }

    @Override
    public void deleteBookById(final Long id) {
        EntityManager entityManager = this.getEntityManager();
        entityManager.getTransaction().begin();
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
        }
        entityManager.getTransaction().commit();
    }

    private EntityManager getEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
