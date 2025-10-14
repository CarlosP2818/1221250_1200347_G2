package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.UserMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.Optional;

@Component
public class UserMongoMapper {

    private static SpringDataUserMongoRepository repo;

    @Autowired
    public UserMongoMapper(SpringDataUserMongoRepository injectedRepo) {
        UserMongoMapper.repo = injectedRepo;
    }

    public static User toDomain(UserMongo mongo) {
        if (mongo == null) return null;

        User user = User.fromEncodedPassword(mongo.getUsername(), mongo.getPassword());
        user.setEnabled(mongo.isEnabled());

        if (mongo.getName() != null)
            user.setName(mongo.getName());

        if (mongo.getAuthorities() != null && !mongo.getAuthorities().isEmpty())
            mongo.getAuthorities().forEach(user::addAuthority);

        return user;
    }

    public static UserMongo toMongo(User user) {
        if (user == null) return null;

        if (repo != null) {
            Optional<UserMongo> existing = repo.findByUsername(user.getUsername());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        UserMongo mongo = new UserMongo(user.getUsername(), user.getPassword());
        mongo.setEnabled(user.isEnabled());

        if (user.getName() != null) {
            mongo.setName(user.getName().getName());
        }

        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
            for (Role r : user.getAuthorities()) {
                mongo.addAuthority(r);
            }
        }

        return mongo;
    }

}
