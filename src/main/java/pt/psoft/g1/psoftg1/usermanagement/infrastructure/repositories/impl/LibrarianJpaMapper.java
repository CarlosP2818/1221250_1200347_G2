package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.LibrarianJpa;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;

import java.util.Optional;

@Component
public class LibrarianJpaMapper {

    private static SpringDataUserRepository repo;

    @Autowired
    public LibrarianJpaMapper(SpringDataUserRepository injectedRepo) {
        LibrarianJpaMapper.repo = injectedRepo;
    }

    public static Librarian toDomain(LibrarianJpa jpa) {
        if (jpa == null) return null;
        Librarian reader = new Librarian(jpa.getUsername(), jpa.getPassword());
        reader.setName(jpa.getName().getName());
        jpa.getAuthorities().forEach(reader::addAuthority);
        return reader;
    }

    public static LibrarianJpa toJpa(Librarian librarian) {
        if (librarian == null) return null;

        // tenta buscar pelo username
        Optional<LibrarianJpa> existing = repo.findByUsername(librarian.getUsername())
                .map(LibrarianJpa.class::cast); // cast seguro

        return existing.orElseGet(() -> {
            // se não existe, cria novo
            LibrarianJpa jpa = new LibrarianJpa(librarian.getUsername(), librarian.getPassword());
            jpa.setName(librarian.getName().getName());
            librarian.getAuthorities().forEach(jpa::addAuthority);
            return jpa;
        });
    }
}

