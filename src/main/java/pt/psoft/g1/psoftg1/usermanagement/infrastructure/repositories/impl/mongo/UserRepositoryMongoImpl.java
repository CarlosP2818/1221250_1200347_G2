package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.UserMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
@Profile("mongo")
public class UserRepositoryMongoImpl implements UserRepository {

    private final SpringDataUserMongoRepository repo;
    private final MongoTemplate mongoTemplate;

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<UserMongo> mongoList =
                ((List<S>)entities).stream()
                        .map(UserMongoMapper::toMongo)
                        .collect(Collectors.toList());
        List<UserMongo> saved = repo.saveAll(mongoList);
        return saved.stream()
                .map(UserMongoMapper::toDomain)
                .map(u -> (S) u)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends User> S save(S entity) {
        UserMongo mongo = UserMongoMapper.toMongo(entity);
        UserMongo saved = repo.save(mongo);
        return (S) UserMongoMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long objectId) {
        return repo.findById(objectId.toString())
                .map(UserMongoMapper::toDomain);
    }

    @Override
    public User getById(Long id) {
        return UserRepository.super.getById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username)
                .map(UserMongoMapper::toDomain);
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        Query mongoQuery = new Query();

        if (query.getUsername() != null && !query.getUsername().isBlank()) {
            mongoQuery.addCriteria(Criteria.where("username").is(query.getUsername()));
        }

        if (query.getFullName() != null && !query.getFullName().isBlank()) {
            mongoQuery.addCriteria(Criteria.where("name.name")
                    .regex(query.getFullName(), "i")); // case-insensitive
        }

        mongoQuery.skip((page.getNumber() - 1) * page.getLimit())
                .limit(page.getLimit());

        List<UserMongo> results = mongoTemplate.find(mongoQuery, UserMongo.class);
        return results.stream()
                .map(UserMongoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByNameName(String name) {
        return repo.findAll().stream()
                .filter(u -> u.getName() != null && u.getName().equalsIgnoreCase(name))
                .map(UserMongoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        return repo.findAll().stream()
                .filter(u -> u.getName() != null && u.getName().toLowerCase().contains(name.toLowerCase()))
                .map(UserMongoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(User user) {
        repo.findByUsername(user.getUsername())
                .ifPresent(repo::delete);
    }
}
