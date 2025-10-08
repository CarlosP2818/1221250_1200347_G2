package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingNumberEmbeddable;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

import java.time.LocalDate;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames={"LENDING_NUMBER"})
}, name = "Lending")
@Getter
@Setter
public class LendingJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Embedded
    private LendingNumberEmbeddable lendingNumber;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private BookJpa book;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    private ReaderDetails readerDetails;

    @Column(nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate limitDate;

    private LocalDate returnedDate;

    @Version
    private long version;

    @Column(length = 1024)
    @Getter
    private String commentary;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    @Getter
    private Integer daysOverdue;

    private int fineValuePerDayInCents;

    protected LendingJpa() {}

    public LendingJpa(LendingNumberEmbeddable lendingNumber, BookJpa book, ReaderDetails readerDetails,
                      LocalDate startDate, LocalDate limitDate, LocalDate returnedDate,
                      String commentary, int fineValuePerDayInCents) {
        this.lendingNumber = lendingNumber;
        this.book = book;
        this.readerDetails = readerDetails;
        this.startDate = startDate;
        this.limitDate = limitDate;
        this.returnedDate = returnedDate;
        this.commentary = commentary;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
    }
}
