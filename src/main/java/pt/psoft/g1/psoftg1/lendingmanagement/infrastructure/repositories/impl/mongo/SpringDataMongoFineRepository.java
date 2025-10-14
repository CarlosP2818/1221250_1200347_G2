package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo.FineMongo;

import java.util.Optional;

public interface SpringDataMongoFineRepository extends MongoRepository<FineMongo, Long> {

    Optional<FineMongo> findByLending_LendingNumber_LendingNumber(String lendingNumber);

}
