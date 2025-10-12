package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.persistence.mongo;

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
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class AuthorRepoMongoImpl implements AuthorRepository {

    private MongoTemplate mongoTemplate;

    private AuthorMongoMapper mapper;

    @Autowired
    public AuthorRepoMongoImpl(MongoTemplate mongoTemplate, AuthorMongoMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public Optional<Author> findByAuthorNumber(String authorNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorNumber").is(authorNumber));
        AuthorMongo authorMongo = mongoTemplate.findOne(query, AuthorMongo.class);
        return Optional.ofNullable(authorMongo).map(mapper::toDomain);
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^" + name, "i"));
        List<AuthorMongo> results = mongoTemplate.find(query, AuthorMongo.class);
        return results.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        List<AuthorMongo> results = mongoTemplate.find(query, AuthorMongo.class);
        return results.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Author save(Author author) {
        System.out.println("Saving author: " + author);
        AuthorMongo entity = mapper.toEntity(author);
        mongoTemplate.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Iterable<Author> findAll() {
        List<AuthorMongo> authors = mongoTemplate.findAll(AuthorMongo.class);
        return authors.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return null;
    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(String authorNumber) {
        return List.of();
    }
}
