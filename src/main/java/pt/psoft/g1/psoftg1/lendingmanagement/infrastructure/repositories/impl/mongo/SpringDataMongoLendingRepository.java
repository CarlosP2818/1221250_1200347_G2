package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo.LendingMongo;

import java.util.Optional;

public interface SpringDataMongoLendingRepository extends MongoRepository<LendingMongo, String> {

    Optional<LendingMongo> findByLendingNumber_LendingNumber(String lendingNumber);

}
