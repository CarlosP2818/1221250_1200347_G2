package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineJpa;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.Optional;


public interface SpringDataFineRepository extends JpaRepository<FineJpa, Long> {

    @Query("SELECT f " +
            "FROM FineJpa f " +
            "JOIN LendingJpa l ON f.lending.pk = l.pk " +
            "WHERE l.lendingNumber.lendingNumber = :lendingNumber")
    Optional<FineJpa> findByLendingNumber(String lendingNumber);

}
