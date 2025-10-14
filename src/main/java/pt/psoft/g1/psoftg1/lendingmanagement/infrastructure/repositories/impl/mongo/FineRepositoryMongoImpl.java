package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.Optional;

@Repository
@Profile("mongo")
public class FineRepositoryMongoImpl implements FineRepository {

    private final MongoTemplate mongoTemplate;

    public FineRepositoryMongoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        return Optional.ofNullable(mongoTemplate.findById(lendingNumber, Fine.class));
    }

    @Override
    public Iterable<Fine> findAll() {
        return mongoTemplate.findAll(Fine.class);
    }

    @Override
    public Fine save(Fine fine) {
        mongoTemplate.save(fine);
        return fine;
    }
}
