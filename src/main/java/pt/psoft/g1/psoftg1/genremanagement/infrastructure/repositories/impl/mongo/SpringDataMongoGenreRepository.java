package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.mongo.GenreMongo;

import java.util.Optional;

@Repository
public interface SpringDataMongoGenreRepository extends MongoRepository<GenreMongo, Long> {
    Optional<GenreMongo> findByGenre(String genre);
    void deleteByGenre(String genre);
}
