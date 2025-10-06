package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.persistence.mongo;

import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.persistense.mongo.GenreMongo;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "book")
public class BookMongo {

    @Id
    private String pk; // Mongo ObjectId

    @Version
    @Getter
    private Long version;

    private Isbn isbn;

    @Getter
    private Title title;

    @Getter
    @DBRef
    private GenreMongo genre; // referência a documento Genre

    @Getter
    @DBRef
    private List<AuthorMongo> authors = new ArrayList<>(); // referência a Authors

    private Description description;

    @Indexed(unique = true)
    private String isbnIndex; // índice único para ISBN

    protected BookMongo() {
        // Construtor default para Mongo
    }

    public String getIsbn() {
        return this.isbn.toString();
    }


    private void setTitle(String title) { this.title = new Title(title); }
    private void setIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        this.isbnIndex = isbn;
    }
    private void setDescription(String description) { this.description = new Description(description); }
    private void setGenre(GenreMongo genre) { this.genre = genre; }
    private void setAuthors(List<AuthorMongo> authors) { this.authors = authors; }
}
