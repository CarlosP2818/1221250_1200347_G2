package pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa;

import jakarta.persistence.Column; import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable public class NameEmbeddable {

    @Column(name = "NAME", length = 150, nullable = false)
    private String name;

    protected NameEmbeddable() {
        // for JPA
    }

    public NameEmbeddable(String name) {
        this.name = name;
    }

}
