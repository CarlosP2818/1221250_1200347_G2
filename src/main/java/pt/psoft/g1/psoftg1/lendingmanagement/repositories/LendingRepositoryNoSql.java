package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LendingRepositoryNoSql implements LendingRepository {

    private final MongoTemplate mongoTemplate;

    public LendingRepositoryNoSql(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        return Optional.ofNullable(mongoTemplate.findById(lendingNumber, Lending.class));
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        return List.of();
    }

    @Override
    public int getCountFromCurrentYear() {
        return 0;
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        return List.of();
    }

    @Override
    public Double getAverageDuration() {
        return 0.0;
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        return 0.0;
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        return List.of();
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public Lending save(Lending lending) {
        return mongoTemplate.save(lending);
    }

    @Override
    public void delete(Lending lending) {
        mongoTemplate.remove(lending);
    }
}
