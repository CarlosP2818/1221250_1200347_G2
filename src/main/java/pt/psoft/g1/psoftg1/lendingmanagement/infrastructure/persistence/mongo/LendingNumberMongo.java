package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Getter
@Setter
public class LendingNumberMongo implements Serializable {

    @Field("lendingNumber")
    private String lendingNumber;

    public LendingNumberMongo(String lendingNumber) {
        this.lendingNumber = lendingNumber;
    }

    protected LendingNumberMongo() {}
}
