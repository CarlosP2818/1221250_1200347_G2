package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingNumberEmbeddable;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

import java.util.Optional;

@Component
public class FineJpaMapper {

    private static SpringDataFineRepository repo;

    @Autowired
    public FineJpaMapper(SpringDataFineRepository injectedRepo) {
        FineJpaMapper.repo = injectedRepo;
    }

    public static Fine toDomain(FineJpa jpa) {
        if (jpa == null) return null;

        Fine fine = new Fine();
        fine.setLending(LendingJpaMapper.toDomain(jpa.getLending()));

        return fine;
    }

    public static FineJpa toJpa(Fine fine) {
        if (fine == null) return null;

        // Tenta reutilizar um existente, se possível
        if (repo != null && fine.getLending().getLendingNumber() != null) {
            Optional<FineJpa> existing = repo.findByLendingNumber(fine.getLending().getLendingNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        return new FineJpa(
                LendingJpaMapper.toJpa(fine.getLending())
        );
    }
}
