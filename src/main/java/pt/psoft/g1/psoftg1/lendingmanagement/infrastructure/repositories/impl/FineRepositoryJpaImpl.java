package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("sql")
public class FineRepositoryJpaImpl implements FineRepository {

    private final SpringDataFineRepository jpaRepository;
    private final EntityManager em;

    @Override
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        return jpaRepository.findByLendingNumber(lendingNumber)
                .map(FineJpaMapper::toDomain);
    }

    @Override
    public Iterable<Fine> findAll() {
        return jpaRepository.findAll().stream().map(FineJpaMapper::toDomain).toList();
    }

    @Override
    public Fine save(Fine lending) {
        var saved = jpaRepository.save(FineJpaMapper.toJpa(lending));
        return FineJpaMapper.toDomain(saved);
    }
}
