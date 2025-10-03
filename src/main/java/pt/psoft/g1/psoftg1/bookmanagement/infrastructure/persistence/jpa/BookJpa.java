package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Book", uniqueConstraints = {
        @UniqueConstraint(name = "uc_book_isbn", columnNames = {"ISBN"})
})
@Setter
@Getter
public class BookJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long pk;

    @Version
    @Getter
    private Long version;

    @Embedded
    IsbnEmbedded isbn;

    @Getter
    @Embedded
    @NotNull
    TitleEmbedded title;

    @Getter
    @ManyToOne
    @NotNull
    GenreJpa genre;

    @Getter
    @ManyToMany
    private List<AuthorJpa> authors = new ArrayList<>();

    @Embedded
    DescriptionEmbedded description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "photo_id")
    @Getter
    private Photo photo;

    public BookJpa(String isbn, String title, String description, GenreJpa genre, List<AuthorJpa> authors, Photo photoURI) {
        setTitle(new TitleEmbedded(title));
        setIsbn(new IsbnEmbedded(isbn));
        if(description != null)
            setDescription(new DescriptionEmbedded(description));
        if(genre==null)
            throw new IllegalArgumentException("Genre cannot be null");
        setGenre(genre);
        if(authors == null)
            throw new IllegalArgumentException("Author list is null");
        if(authors.isEmpty())
            throw new IllegalArgumentException("Author list is empty");

        setAuthors(authors);
        setPhoto(photoURI);
    }

    protected BookJpa() {
        // got ORM only
    }
}
