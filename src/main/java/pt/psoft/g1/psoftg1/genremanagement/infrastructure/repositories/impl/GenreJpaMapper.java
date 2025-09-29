package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
//TODO: Mapper com MapStruct (apenas quando todos os domain estiverem desacoplados das JPA)
@Component
public class GenreJpaMapper {
    public static Genre toDomain(GenreJpa jpa) {
        if (jpa == null) return null;
        return new Genre(jpa.getGenre());
    }

    public static GenreJpa toJpa(Genre genre) {
        if (genre == null) return null;
        return new GenreJpa(genre.getGenre());
    }
}
