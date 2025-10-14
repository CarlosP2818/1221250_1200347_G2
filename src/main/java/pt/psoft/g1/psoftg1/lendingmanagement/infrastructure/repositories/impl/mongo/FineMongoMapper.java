package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo.FineMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.LendingJpaMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineJpa;

import java.util.Optional;

public class FineMongoMapper {

    private static SpringDataMongoFineRepository repo;

    @Autowired
    public FineMongoMapper(SpringDataMongoFineRepository injectedRepo) {
        FineMongoMapper.repo = injectedRepo;
    }

    public static Fine toDomain(FineJpa jpa) {
        if (jpa == null) return null;

        Fine fine = new Fine();
        fine.setLending(LendingJpaMapper.toDomain(jpa.getLending()));

        return fine;
    }

    public static FineMongo toMongo(Fine fine) {
        if (fine == null) return null;

        // Tenta reutilizar um existente, se possível
        if (repo != null && fine.getLending().getLendingNumber() != null) {
            Optional<FineMongo> existing = repo.findByLending_LendingNumber_LendingNumber(fine.getLending().getLendingNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        return new FineMongo(
                LendingMongoMapper.toMongo(fine.getLending())
        );
    }
}
