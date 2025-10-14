package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document(collection = "fines")
public class FineMongo {

    @Id
    private String id;

    private int fineValuePerDayInCents;
    private int centsValue;

    @DBRef
    private LendingMongo lending;

    public FineMongo(LendingMongo lending) {
        this.lending = Objects.requireNonNull(lending);
    }

    protected FineMongo() {}
}
