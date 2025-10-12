package pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.UserJpa;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

@Entity
@DiscriminatorValue("READER")
public class ReaderJpa extends UserJpa {
    protected ReaderJpa() {
        // for ORM only
    }
    public ReaderJpa(String username, String password) {
        super(username, password);
        this.addAuthority(new Role(Role.READER));
    }

    /**
     * factory method. since mapstruct does not handle protected/private setters
     * neither more than one public constructor, we use these factory methods for
     * helper creation scenarios
     *
     * @param username
     * @param password
     * @param name
     * @return
     */

    public static ReaderJpa newReader(final String username, final String password, final String name) {
        final var u = new ReaderJpa(username, password);
        u.setName(name);
        return u;
    }
}
