package pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Genre")
@Getter
@NoArgsConstructor
public class GenreJpa implements Serializable {
    @Transient
    private final int GENRE_MAX_LENGTH = 100;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long pk;

    @Size(min = 1, max = GENRE_MAX_LENGTH, message = "Genre name must be between 1 and 100 characters")
    @Column(unique=true, nullable=false, length = GENRE_MAX_LENGTH)
    String genre;

    public GenreJpa(String genre) {
        this.genre = genre;
    }
}
