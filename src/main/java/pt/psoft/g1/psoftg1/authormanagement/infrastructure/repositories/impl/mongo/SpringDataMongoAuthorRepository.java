package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public interface SpringDataMongoAuthorRepository extends MongoRepository<AuthorMongo, Long> {
    Optional<AuthorMongo> findByAuthorNumber(String authorNumber);
    List<AuthorMongo> findByName_NameStartsWith(String name);
    List<AuthorMongo> findByName_Name(String name);
}
