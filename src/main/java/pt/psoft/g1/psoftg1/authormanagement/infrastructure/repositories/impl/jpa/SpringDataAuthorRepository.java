package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;

import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends JpaRepository<AuthorJpa, Long> {
    Optional<AuthorJpa> findByAuthorNumber(Long authorNumber);

    List<AuthorJpa> findByName_NameStartsWith(String name);

    List<AuthorJpa> findByName_Name(String name);
}

