package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo.BookMongoMapper;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo.LendingMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.mongo.LendingNumberMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.ReaderDetailsJpaMapper;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mongo.ReaderDetailsMongoMapper;

import java.util.Optional;

public class LendingMongoMapper {

    private static SpringDataMongoLendingRepository repo;

    @Autowired
    public LendingMongoMapper(SpringDataMongoLendingRepository injectedRepo) {
        LendingMongoMapper.repo = injectedRepo;
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

    public static LendingMongo toMongo(Lending lending) {

        if (lending == null) return null;

        if (repo != null && lending.getLendingNumber() != null) {
            Optional<LendingMongo> existing = repo.findByLendingNumber_LendingNumber(lending.getLendingNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        return new LendingMongo(
                new LendingNumberMongo(lending.getLendingNumber()),
                BookMongoMapper.toMongo(lending.getBook()),
                ReaderDetailsMongoMapper.toMongo(lending.getReaderDetails()),
                lending.getStartDate(),
                lending.getLimitDate(),
                lending.getReturnedDate(),
                lending.getCommentary(),
                lending.getFineValuePerDayInCents()
        );
    }
}
