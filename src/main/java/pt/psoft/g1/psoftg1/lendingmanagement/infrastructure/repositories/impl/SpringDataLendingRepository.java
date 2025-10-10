package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataLendingRepository extends CrudRepository<LendingJpa, Long> {

    @Query("SELECT l FROM LendingJpa l WHERE l.lendingNumber.lendingNumber = :lendingNumber")
    Optional<LendingJpa> findByLendingNumber_LendingNumber(@Param("lendingNumber") String lendingNumber);

    @Query("SELECT l FROM LendingJpa l " +
            "JOIN BookJpa b ON l.book.pk = b.pk " +
            "JOIN ReaderDetailsJpa r ON l.readerDetails.pk = r.pk " +
            "WHERE b.isbn.isbn = :isbn AND r.readerNumber.readerNumber = :readerNumber")
    List<LendingJpa> listByReaderNumberAndIsbn(@Param("readerNumber") String readerNumber, @Param("isbn") String isbn);

    @Query("SELECT COUNT(l) FROM LendingJpa l WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Query("SELECT l FROM LendingJpa l " +
            "JOIN ReaderDetailsJpa r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber AND l.returnedDate IS NULL")
    List<LendingJpa> listOutstandingByReaderNumber(@Param("readerNumber") String readerNumber);

    @Query(value = "SELECT AVG(DATEDIFF(day, l.start_date, l.returned_date)) FROM Lending l", nativeQuery = true)
    Double getAverageDuration();

    @Query(value =
            "SELECT AVG(DATEDIFF(day, l.start_date, l.returned_date)) " +
                    "FROM Lending l JOIN BOOK b ON l.BOOK_PK = b.PK WHERE b.ISBN = :isbn",
            nativeQuery = true)
    Double getAvgLendingDurationByIsbn(@Param("isbn") String isbn);

    // List<ReaderAverageDto> getAverageMonthlyPerReader(LocalDate startDate, LocalDate endDate);
}
