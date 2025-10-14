package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.ReaderJpa;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.ReaderMongo;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public interface SpringDataReaderMongoRepository extends MongoRepository<ReaderMongo, Long> {

    Optional<ReaderMongo> findByUsername(String username);
}
