package pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class UserMongo implements UserDetails {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean enabled=true;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String name;

    @Getter
    private Set<Role> authorities = new HashSet<>();

    public UserMongo() {
        // for ORM
    }

    public UserMongo(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public static UserMongo fromEncodedPassword(String username, String encodedPassword) {
        UserMongo u = new UserMongo();
        u.username = username;
        u.password = encodedPassword;
        return u;
    }

    public static UserMongo newUser(final String username, final String password, final String name) {
        final var u = new UserMongo(username, password);
        u.setName(name);
        return u;
    }

    public static UserMongo newUser(final String username, final String password, final String name, final String role) {
        final var u = new UserMongo(username, password);
        u.setName(name);
        u.addAuthority(new Role(role));
        return u;
    }

    public void setPassword(final String password) {
        final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void addAuthority(final Role r) {
        authorities.add(r);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


}
