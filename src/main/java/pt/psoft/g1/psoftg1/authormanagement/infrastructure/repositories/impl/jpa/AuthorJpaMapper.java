package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.BioEmbeddable;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.jpa.ReaderDetailsJpa;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.ReaderDetailsJpaMapper;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.SpringDataReaderDetailsRepository;
import pt.psoft.g1.psoftg1.shared.infrastructure.persistence.jpa.NameEmbeddable;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.Optional;

@Component
public class AuthorJpaMapper {

    private static SpringDataAuthorRepository repo;

    @Autowired
    public AuthorJpaMapper(SpringDataAuthorRepository injectedRepo) {
        AuthorJpaMapper.repo = injectedRepo;
    }

    public static Author toDomain(AuthorJpa jpa) {
        // You need a domain constructor or package-private setters that accept id and version.
        // Example (to add in domain):
        // Author(Long authorNumber, Long version, String name, String bio, String photoUri)
        String photoUri = jpa.getPhoto() != null ? jpa.getPhoto().getPhotoFile() : null;
        Author domain = new Author(jpa.getName().getName(), jpa.getBio().getBio(), photoUri);
        // TODO: set id and version on domain (requires domain support)
        domain.setAuthorNumber(jpa.getAuthorNumber()); // suggest package-private setter
        domain.setVersion(jpa.getVersion());
        return domain;
    }

    public static AuthorJpa toJpa(Author domain) {

        if (repo != null) {
            Optional<AuthorJpa> existing = repo.findByAuthorNumber(domain.getAuthorNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        final var name = new NameEmbeddable(domain.getName());
        final var bio = new BioEmbeddable(domain.getBio());
        final var jpa = new AuthorJpa(name, bio, domain.getPhoto());// safe if null (for creates)
        // If domain exposes version, set it; JPA @Version will manage increments.
        jpa.setVersion(domain.getVersion());
        return jpa;
    }
}
