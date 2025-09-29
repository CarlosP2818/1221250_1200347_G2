package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends JpaRepository<AuthorJpa, Long> {
    Optional<AuthorJpa> findByAuthorNumber(Long authorNumber);

    List<AuthorJpa> findByName_NameStartsWith(String name);

    List<AuthorJpa> findByName_Name(String name); // Queries that require Book/Lending mappings (top/co-authors) will be added once their JPA models exist.
}

