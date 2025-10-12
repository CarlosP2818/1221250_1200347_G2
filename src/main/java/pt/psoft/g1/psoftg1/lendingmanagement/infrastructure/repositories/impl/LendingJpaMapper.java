package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingNumberEmbeddable;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.ReaderDetailsJpaMapper;

import java.util.Optional;

@Component
public class LendingJpaMapper {

    private static SpringDataLendingRepository repo;

    @Autowired
    public LendingJpaMapper(SpringDataLendingRepository injectedRepo) {
        LendingJpaMapper.repo = injectedRepo;
    }

    public static Lending toDomain(LendingJpa jpa) {
        if (jpa == null) return null;

        Lending lending = new Lending();
        lending.setPk(jpa.getPk());
        lending.setLendingNumber(new LendingNumber(jpa.getLendingNumber().getLendingNumber()));
        lending.setBook(BookJpaMapper.toDomain(jpa.getBook()));
        lending.setReaderDetails(ReaderDetailsJpaMapper.toDomain(jpa.getReaderDetails()));
        lending.setStartDate(jpa.getStartDate());
        lending.setLimitDate(jpa.getLimitDate());
        lending.setReturnedDate(jpa.getReturnedDate());
        lending.setCommentary(jpa.getCommentary());
        lending.setFineValuePerDayInCents(jpa.getFineValuePerDayInCents());
        lending.setVersion(jpa.getVersion());

        return lending;
    }

    public static LendingJpa toJpa(Lending lending) {
        if (lending == null) return null;

        // Tenta reutilizar um existente, se possível
        if (repo != null && lending.getLendingNumber() != null) {
            Optional<LendingJpa> existing = repo.findByLendingNumber_LendingNumber(lending.getLendingNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        return new LendingJpa(
                new LendingNumberEmbeddable(lending.getLendingNumber()),
                BookJpaMapper.toJpa(lending.getBook()),
                ReaderDetailsJpaMapper.toJpa(lending.getReaderDetails()),
                lending.getStartDate(),
                lending.getLimitDate(),
                lending.getReturnedDate(),
                lending.getCommentary(),
                lending.getFineValuePerDayInCents()
        );
    }
}
