package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.SpringDataAuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("mongo")
public class AuthorRepoMongoImpl implements AuthorRepository {

    private final SpringDataMongoAuthorRepository mongoRepository;

    @Override
    public Optional<Author> findByAuthorNumber(String authorNumber) {
        return mongoRepository.findByAuthorNumber(authorNumber).map(AuthorMongoMapper::toDomain);
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        return mongoRepository.findByName_NameStartsWith(name)
                .stream().map(AuthorMongoMapper::toDomain).toList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        return mongoRepository.findByName_Name(name)
                .stream().map(AuthorMongoMapper::toDomain).toList();
    }

    @Override
    public Author save(Author author) {
        System.out.println("Saving author: " + author);
        final var saved = mongoRepository.save(AuthorMongoMapper.toMongo(author));
        return AuthorMongoMapper.toDomain(saved);
    }

    @Override
    public Iterable<Author> findAll() {
        return mongoRepository.findAll().stream().map(AuthorMongoMapper::toDomain).toList();
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return null;
    }

    @Override
    public void delete(Author author) {
        mongoRepository.delete(AuthorMongoMapper.toMongo(author));
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(String authorNumber) {
        return List.of();
    }
}
