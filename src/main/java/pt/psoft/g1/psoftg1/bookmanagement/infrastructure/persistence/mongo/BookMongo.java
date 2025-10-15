package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.DescriptionEmbedded;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.IsbnEmbedded;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.TitleEmbedded;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.mongo.GenreMongo;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
@Setter
@Getter
public class BookMongo {

    @Id
    String pk;

    @Version
    private Long version;

    IsbnMongo isbn;

    TitleMongo title;

    GenreMongo genre;

    @DBRef
    private List<AuthorMongo> authors = new ArrayList<>();

    DescriptionMongo description;

    private Photo photo;

    public BookMongo() {
        // got ORM only
    }

    public BookMongo(String isbn, String title, String description, GenreMongo genre, List<AuthorMongo> authors, Photo photo) {
        setTitle(new TitleMongo(title));
        setIsbn(new IsbnMongo(isbn));
        if(description != null)
            setDescription(new DescriptionMongo(description));
        if(genre==null)
            throw new IllegalArgumentException("Genre cannot be null");
        setGenre(genre);
        if(authors == null)
            throw new IllegalArgumentException("Author list is null");
        if(authors.isEmpty())
            throw new IllegalArgumentException("Author list is empty");
        setAuthors(authors);
        this.photo = photo;
    }


}
