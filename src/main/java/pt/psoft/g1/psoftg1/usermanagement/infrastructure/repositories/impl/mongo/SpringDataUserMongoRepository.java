package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.UserMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataUserMongoRepository extends MongoRepository<UserMongo, Long> {

    @Override
    <S extends UserMongo> List<S> saveAll(Iterable<S> entities);

    @Override
    <S extends UserMongo> S save(S entity);

    Optional<UserMongo> findById(String id);

    default UserMongo getById(final String id) {
        return findById(id)
                .filter(UserMongo::isEnabled)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    Optional<UserMongo> findByUsername(String username);

    List<User> findByName(String name);

    List<User> findByNameContains(String name);

}
