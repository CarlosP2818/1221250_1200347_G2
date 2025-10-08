package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.persistence.jpa.LendingJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.jpa.SpringDataLendingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LendingRepositoryJpaImpl implements LendingRepository {

    private final SpringDataLendingRepository jpaRepository;
    private final EntityManager em;

    @Override
    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        return jpaRepository.findByLendingNumber_LendingNumber(lendingNumber)
                .map(LendingJpaMapper::toDomain);
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        return jpaRepository.listByReaderNumberAndIsbn(readerNumber, isbn)
                .stream().map(LendingJpaMapper::toDomain).toList();
    }

    @Override
    public int getCountFromCurrentYear() {
        return jpaRepository.getCountFromCurrentYear();
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        return jpaRepository.listOutstandingByReaderNumber(readerNumber)
                .stream().map(LendingJpaMapper::toDomain).toList();
    }

    @Override
    public Double getAverageDuration() {
        return jpaRepository.getAverageDuration();
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        return jpaRepository.getAvgLendingDurationByIsbn(isbn);
    }

    public List<Lending> getOverdue(Page page) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<LendingJpa> cq = cb.createQuery(LendingJpa.class);
        final Root<LendingJpa> root = cq.from(LendingJpa.class);
        cq.select(root);

        final List<Predicate> where = new ArrayList<>();

        // Select overdue lendings where returnedDate is null and limitDate is before the current date
        where.add(cb.isNull(root.get("returnedDate")));
        where.add(cb.lessThan(root.get("limitDate"), LocalDate.now()));

        cq.where(where.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("limitDate"))); // Order by limitDate, oldest first

        final TypedQuery<LendingJpa> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList().stream().map(LendingJpaMapper::toDomain).toList();
    }

    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned,
                                        LocalDate startDate, LocalDate endDate) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Lending> cq = cb.createQuery(Lending.class);
        final Root<Lending> lendingRoot = cq.from(Lending.class);
        final Join<Lending, Book> bookJoin = lendingRoot.join("book");
        final Join<Lending, ReaderDetails> readerDetailsJoin = lendingRoot.join("readerDetails");
        cq.select(lendingRoot);

        final List<Predicate> where = new ArrayList<>();

        if (StringUtils.hasText(readerNumber))
            where.add(cb.like(readerDetailsJoin.get("readerNumber").get("readerNumber"), readerNumber));
        if (StringUtils.hasText(isbn))
            where.add(cb.like(bookJoin.get("isbn").get("isbn"), isbn));
        if (returned != null){
            if(returned){
                where.add(cb.isNotNull(lendingRoot.get("returnedDate")));
            }else{
                where.add(cb.isNull(lendingRoot.get("returnedDate")));
            }
        }
        if(startDate!=null)
            where.add(cb.greaterThanOrEqualTo(lendingRoot.get("startDate"), startDate));
        if(endDate!=null)
            where.add(cb.lessThanOrEqualTo(lendingRoot.get("startDate"), endDate));

        cq.where(where.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(lendingRoot.get("lendingNumber")));

        final TypedQuery<Lending> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList();
    }

    @Override
    public Lending save(Lending lending) {
        var saved = jpaRepository.save(LendingJpaMapper.toJpa(lending));
        return LendingJpaMapper.toDomain(saved);
    }

    @Override
    public void delete(Lending lending) {
        if (lending.getPk() != null) {
            jpaRepository.deleteById(lending.getPk());
        }
    }
}
