package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class LendingNumberEmbeddable implements Serializable {

    /**
     * Natural key of a {@code Lending}.
     */
    @Column(name = "LENDING_NUMBER", length = 32)
    @NotNull
    @NotBlank
    @Size(min = 6, max = 32)
    @Getter
    private String lendingNumber;


    public LendingNumberEmbeddable(String lendingNumber) {
        this.lendingNumber = lendingNumber;
    }

    /**Protected empty constructor for ORM only.*/
    public LendingNumberEmbeddable() {}
}
