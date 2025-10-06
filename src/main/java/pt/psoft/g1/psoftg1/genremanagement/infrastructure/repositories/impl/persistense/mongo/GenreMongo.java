package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.persistense.mongo;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "genre")
public class GenreMongo {

    private static final int GENRE_MAX_LENGTH = 100;

    @Id
    private String pk; // Mongo ObjectId

    @Size(min = 1, max = GENRE_MAX_LENGTH, message = "Genre name must be between 1 and 100 characters")
    @Indexed(unique = true)
    @Getter
    private String genre;

    protected GenreMongo() {
        // Construtor default para MongoDB
    }

    public GenreMongo(String genre) {
        setGenre(genre);
    }

    private void setGenre(String genre) {
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        if (genre.isBlank())
            throw new IllegalArgumentException("Genre cannot be blank");
        if (genre.length() > GENRE_MAX_LENGTH)
            throw new IllegalArgumentException("Genre has a maximum of " + GENRE_MAX_LENGTH + " characters");
        this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }
}
