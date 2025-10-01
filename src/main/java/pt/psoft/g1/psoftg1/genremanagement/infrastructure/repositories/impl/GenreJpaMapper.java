package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.Optional;

// TODO: Mapper com MapStruct (apenas quando todos os domain estiverem desacoplados das JPA)
@Component
public class GenreJpaMapper {
    // Inject SpringData repository to resolve existing managed GenreJpa by unique name
    private static SpringDataGenreRepository repo;

    @Autowired
    public GenreJpaMapper(SpringDataGenreRepository injectedRepo) {
        GenreJpaMapper.repo = injectedRepo;
    }

    public static Genre toDomain(GenreJpa jpa) {
        if (jpa == null) return null;
        return new Genre(jpa.getGenre());
    }

    public static GenreJpa toJpa(Genre genre) {
        if (genre == null) return null;
        // Prefer returning the existing managed entity to avoid transient reference issues
        if (repo != null) {
            Optional<GenreJpa> existing = repo.findByGenre(genre.getGenre());
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        // Fallback: create a new instance (will only be persisted if cascaded)
        return new GenreJpa(genre.getGenre());
    }
}
