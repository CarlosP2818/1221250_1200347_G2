package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class IsbnEmbedded implements Serializable {
    @Size(min = 10, max = 13)
    @Column(name="ISBN", length = 16)

    String isbn;

    public IsbnEmbedded(String isbn) {
            this.isbn = isbn;
    }

    protected IsbnEmbedded() {};


}