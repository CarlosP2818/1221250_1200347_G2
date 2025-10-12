package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.UserJpa;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.Optional;

@Component
public class UserJpaMapper {

    private static SpringDataUserRepository repo;

    @Autowired
    public UserJpaMapper(SpringDataUserRepository injectedRepo) {
        UserJpaMapper.repo = injectedRepo;
    }

    public static User toDomain(UserJpa jpa) {
        if (jpa == null) return null;

        User user = User.fromEncodedPassword(jpa.getUsername(), jpa.getPassword());
        user.setEnabled(jpa.isEnabled());

        if (jpa.getName() != null)
            user.setName(jpa.getName().getName());

        if (jpa.getAuthorities() != null && !jpa.getAuthorities().isEmpty())
            jpa.getAuthorities().forEach(user::addAuthority);

        user.setId(jpa.getId());
        return user;
    }

    public static UserJpa toJpa(User user) {
        if (user == null) return null;

        if (repo != null) {
            Optional<UserJpa> existing = repo.findByUsername(user.getUsername());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        UserJpa jpa = new UserJpa(user.getUsername(), user.getPassword());
        jpa.setEnabled(user.isEnabled());

        if (user.getName() != null) {
            jpa.setName(user.getName().getName());
        }

        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
            for (Role r : user.getAuthorities()) {
                jpa.addAuthority(r);
            }
        }

        return jpa;
    }
}
