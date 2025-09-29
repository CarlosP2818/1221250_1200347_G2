package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpaImpl implements AuthorRepository {
    private final SpringDataAuthorRepository jpaRepository;

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        return jpaRepository.findByAuthorNumber(authorNumber).map(AuthorJpaMapper::toDomain);
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        return jpaRepository.findByName_NameStartsWith(name)
                .stream().map(AuthorJpaMapper::toDomain).toList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        return jpaRepository.findByName_Name(name)
                .stream().map(AuthorJpaMapper::toDomain).toList();
    }

    @Override
    public Author save(Author author) {
        final var saved = jpaRepository.save(AuthorJpaMapper.toJpa(author));
        return AuthorJpaMapper.toDomain(saved);
    }

    @Override
    public Iterable<Author> findAll() {
        return jpaRepository.findAll().stream().map(AuthorJpaMapper::toDomain).toList();
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        // Implement after BookJpa/LendingJpa exist and a projection is defined,
        // or move this query to a dedicated read-model repository.
        throw new UnsupportedOperationException("FindTopAuthorByLendings not implemented in the JPA adapter yet");
    }

    @Override
    public void delete(Author author) {
        if (author.getId() != null) {
            jpaRepository.deleteById(author.getId());
        }
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        // Implement after BookJpa exists, with a JPQL query on JPA entities.
        throw new UnsupportedOperationException("findCoAuthorsByAuthorNumber not implemented in the JPA adapter yet");
    }
}
