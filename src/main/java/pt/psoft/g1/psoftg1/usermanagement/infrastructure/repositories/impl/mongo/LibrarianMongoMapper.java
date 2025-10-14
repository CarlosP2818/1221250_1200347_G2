package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.LibrarianMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;

@Component
public class LibrarianMongoMapper {

    private static SpringDataUserMongoRepository repo;

    @Autowired
    public LibrarianMongoMapper(SpringDataUserMongoRepository injectedRepo) {
        LibrarianMongoMapper.repo = injectedRepo;
    }

    public static Librarian toDomain(LibrarianMongo mongo) {
        if (mongo == null) return null;

        Librarian librarian = new Librarian(mongo.getUsername(), mongo.getPassword());
        librarian.setEnabled(mongo.isEnabled());

        if (mongo.getName() != null)
            librarian.setName(mongo.getName());

        if (mongo.getAuthorities() != null && !mongo.getAuthorities().isEmpty())
            mongo.getAuthorities().forEach(librarian::addAuthority);

        return librarian;
    }

    public static LibrarianMongo toMongo(Librarian librarian) {
        if (librarian == null) return null;

        LibrarianMongo mongo = new LibrarianMongo(librarian.getUsername(), librarian.getPassword());
        mongo.setEnabled(librarian.isEnabled());

        if (librarian.getName() != null) {
            mongo.setName(librarian.getName().getName());
        }

        if (librarian.getAuthorities() != null && !librarian.getAuthorities().isEmpty()) {
            for (var r : librarian.getAuthorities()) {
                mongo.addAuthority(r);
            }
        }

        return mongo;
    }
}
