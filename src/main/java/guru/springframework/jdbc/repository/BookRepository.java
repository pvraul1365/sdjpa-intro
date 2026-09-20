package guru.springframework.jdbc.repository;

import guru.springframework.jdbc.domain.Book;
import jakarta.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

/**
 * BookRepository
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - raul.perez.vicenet@gmail.com
 * @version 14/09/2026 - 17:47
 * @since 1.25
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    Book readByTitle(String title);

    @Nullable
    Book getByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();

    @Async
    Future<Book> queryByTitle(String title);

    @Query("SELECT b FROM Book b WHERE b.title = ?1")
    Book findBookByTitleWithQuery(String title);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Book findBookByTitleWithQueryNamed(@Param("title") String title);

    @Query(name = "Book.findBookByTitleWithQueryNamed")
    Book jpaNamed(@Param("title") String title);
}
