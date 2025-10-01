package pt.psoft.g1.psoftg1.shared.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;

import java.util.List;
import java.util.Optional;

public class ForbiddenNameRepositoryNoSQL implements ForbiddenNameRepository {

    private final MongoTemplate mongoTemplate;

    public ForbiddenNameRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Iterable<ForbiddenName> findAll() {
        return mongoTemplate.findAll(ForbiddenName.class);
    }

    @Override
    public List<ForbiddenName> findByForbiddenNameIsContained(String pat) {
        return List.of();
    }

    @Override
    public ForbiddenName save(ForbiddenName forbiddenName) {
        mongoTemplate.save(forbiddenName);
        return forbiddenName;
    }

    @Override
    public Optional<ForbiddenName> findByForbiddenName(String forbiddenName) {
        return Optional.empty();
    }

    @Override
    public int deleteForbiddenName(String forbiddenName) {
        return 0;
    }
}
