package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataBookRepository extends JpaRepository<BookJpa, Long> {

    Optional<BookJpa> findByIsbn_Isbn(String isbn);

    @Query("SELECT new pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO(BookJpa , COUNT(l)) " +
            "FROM BookJpa b " +
            "JOIN LendingJpa l ON l.book = b " +
            "WHERE l.startDate > :oneYearAgo " +
            "GROUP BY b " +
            "ORDER BY COUNT(l) DESC")
    Page<BookCountDTO> findTop5BooksLent(@Param("oneYearAgo") LocalDate oneYearAgo, Pageable pageable);

    List<BookJpa> findByGenre_GenreContaining(String genre);

    List<BookJpa> findByTitle_TitleContaining(String title);

    @Query(value =
            "SELECT b.* " +
                    "FROM BOOK b " +
                    "JOIN BOOK_AUTHORS ba on b.pk = ba.BOOK_PK " +
                    "JOIN AUTHOR a on ba.AUTHORS_AUTHOR_NUMBER = a.AUTHOR_NUMBER " +
                    "WHERE a.NAME LIKE :authorName",
            nativeQuery = true)
    List<BookJpa> findByAuthorName(@Param("authorName") String authorName);

    @Query(value =
            "SELECT b.* " +
                    "FROM BOOK b " +
                    "JOIN BOOK_AUTHORS ba on b.pk = ba.BOOK_PK " +
                    "JOIN AUTHOR a on ba.AUTHORS_AUTHOR_NUMBER = a.AUTHOR_NUMBER " +
                    "WHERE a.AUTHOR_NUMBER = :authorNumber",
            nativeQuery = true)
    List<BookJpa> findBooksByAuthorNumber(Long authorNumber);

}
