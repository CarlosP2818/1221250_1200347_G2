package pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Photo;

@Setter
@Getter
@Entity
@Table(name = "Author")
public class AuthorJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_NUMBER")
    private Long authorNumber;

    @Version
    private Long version;

    @Embedded
    private NameEmbeddable name;

    @Embedded
    private BioEmbeddable bio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    protected AuthorJpa() {
        // for JPA
    }

    public AuthorJpa(NameEmbeddable name, BioEmbeddable bio, Photo photo) {
        this.name = name;
        this.bio = bio;
        this.photo = photo;
    }

}