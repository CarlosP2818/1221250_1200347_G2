package pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo;

import pt.psoft.g1.psoftg1.usermanagement.model.Role;

public class ReaderMongo extends UserMongo{

    protected ReaderMongo() { super(); }

    public ReaderMongo(String username, String password) {
        super(username, password);
        this.addAuthority(new Role(Role.READER));
    }

    public static ReaderMongo newReader(final String username, final String password, final String name) {
        final var u = new ReaderMongo(username, password);
        u.setName(name);
        return u;
    }

}
