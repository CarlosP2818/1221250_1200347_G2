package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;

import java.util.Optional;

public class FineRepositoryNoSql implements FineRepository {

    private final MongoTemplate mongoTemplate;

    public FineRepositoryNoSql(MongoTemplate mongoTemplate) {
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
