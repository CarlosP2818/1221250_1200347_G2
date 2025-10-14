package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingNumberEmbeddable;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.jpa.ReaderDetailsJpa;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.mongo.ReaderDetailsMongo;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "lendings")
public class LendingMongo {

    @Id
    private String id;

    @Field("lendingNumber")
    private LendingNumberMongo lendingNumber;

    @DBRef
    private BookMongo book;

    @DBRef
    private ReaderDetailsMongo readerDetails;

    private LocalDate startDate;
    private LocalDate limitDate;
    private LocalDate returnedDate;

    @Version
    private long version;

    @Field("commentary")
    private String commentary;

    private Integer daysUntilReturn;
    private Integer daysOverdue;

    private int fineValuePerDayInCents;

    protected LendingMongo(){}

    public LendingMongo(LendingNumberMongo lendingNumber, BookMongo book, ReaderDetailsMongo readerDetails,
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
