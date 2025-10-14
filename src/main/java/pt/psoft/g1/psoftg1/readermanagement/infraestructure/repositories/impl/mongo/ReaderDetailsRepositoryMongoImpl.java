package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mongo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("mongo")
public class ReaderDetailsRepositoryMongoImpl implements ReaderRepository {

    private final EntityManager em;

    private final SpringDataMongoReaderDetailsRepository repo;

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        return repo.findByReaderNumber(readerNumber).stream().map(ReaderDetailsMongoMapper::toDomain).findFirst();    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber).stream().map(ReaderDetailsMongoMapper::toDomain).toList();
    }

    @Override
    public Optional<ReaderDetails> findByUsername(String username) {
        return repo.findByReader_Username(username).stream().map(ReaderDetailsMongoMapper::toDomain).findFirst();
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        return repo.findByReader_Id(userId).stream().map(ReaderDetailsMongoMapper::toDomain).findFirst();
    }

    @Override
    public int getCountFromCurrentYear() {
        return 0;
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        var saved = repo.save(ReaderDetailsMongoMapper.toMongo(readerDetails));
        return ReaderDetailsMongoMapper.toDomain(saved);
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        return repo.findAll().stream().map(ReaderDetailsMongoMapper::toDomain).toList();
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        throw new UnsupportedOperationException("findTopByGenre not implemented in the Mongo adapter yet");
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
        repo.delete(ReaderDetailsMongoMapper.toMongo(readerDetails));
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ReaderDetails> cq = cb.createQuery(ReaderDetails.class);
        final Root<ReaderDetails> readerDetailsRoot = cq.from(ReaderDetails.class);
        Join<ReaderDetails, User> userJoin = readerDetailsRoot.join("reader");

        cq.select(readerDetailsRoot);

        final List<Predicate> where = new ArrayList<>();
        if (StringUtils.hasText(query.getName())) { //'contains' type search
            where.add(cb.like(userJoin.get("name").get("name"), "%" + query.getName() + "%"));
            cq.orderBy(cb.asc(userJoin.get("name")));
        }
        if (StringUtils.hasText(query.getEmail())) { //'exatct' type search
            where.add(cb.equal(userJoin.get("username"), query.getEmail()));
            cq.orderBy(cb.asc(userJoin.get("username")));

        }
        if (StringUtils.hasText(query.getPhoneNumber())) { //'exatct' type search
            where.add(cb.equal(readerDetailsRoot.get("phoneNumber").get("phoneNumber"), query.getPhoneNumber()));
            cq.orderBy(cb.asc(readerDetailsRoot.get("phoneNumber").get("phoneNumber")));
        }

        // search using OR
        if (!where.isEmpty()) {
            cq.where(cb.or(where.toArray(new Predicate[0])));
        }


        final TypedQuery<ReaderDetails> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList();
    }
}
