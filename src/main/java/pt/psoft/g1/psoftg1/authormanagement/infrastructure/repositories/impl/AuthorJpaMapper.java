package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.BioEmbeddable;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.NameEmbeddable;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@Component
public class AuthorJpaMapper {

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
        final var name = new NameEmbeddable(domain.getName());
        final var bio = new BioEmbeddable(domain.getBio());
        final var jpa = new AuthorJpa(name, bio, domain.getPhoto());
        jpa.setAuthorNumber(domain.getId()); // safe if null (for creates)
        // If domain exposes version, set it; JPA @Version will manage increments.
        jpa.setVersion(domain.getVersion());
        return jpa;
    }
}
