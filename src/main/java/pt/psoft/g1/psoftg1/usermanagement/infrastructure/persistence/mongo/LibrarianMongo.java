package pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo;

import pt.psoft.g1.psoftg1.usermanagement.model.Role;

public class LibrarianMongo extends UserMongo {

    protected LibrarianMongo() { super(); }

    public LibrarianMongo(String username, String password) {
        super(username, password);
    }

    public static LibrarianMongo newLibrarian(final String username, final String password, final String name) {
        final var u = new LibrarianMongo(username, password);
        u.setName(name);
        u.addAuthority(new Role(Role.LIBRARIAN));
        return u;
    }
}
