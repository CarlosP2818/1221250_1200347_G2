package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.mongo.GenreMongo;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.SpringDataGenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.Optional;

public class GenreMongoMapper {

    private static SpringDataMongoGenreRepository repo;

    @Autowired
    public GenreMongoMapper(SpringDataMongoGenreRepository injectedRepo) {
        GenreMongoMapper.repo = injectedRepo;
    }

    public static Genre toDomain(GenreMongo jpa) {
        if (jpa == null) return null;
        return new Genre(jpa.getGenre());
    }

    public static GenreMongo toMongo(Genre genre) {
        if (genre == null) return null;

        if (repo != null) {
            Optional<GenreMongo> existing = repo.findByGenre(genre.getGenre());
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        // Fallback: create a new instance (will only be persisted if cascaded)
        return new GenreMongo(genre.getGenre());
    }
}
