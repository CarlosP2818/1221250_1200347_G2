package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.BioEmbeddable;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.BioMongo;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.SpringDataAuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.SpringDataBookRepository;
import pt.psoft.g1.psoftg1.shared.infrastructure.persistence.jpa.NameEmbeddable;
import pt.psoft.g1.psoftg1.shared.model.Name;

import java.util.Optional;

@Component
@Profile("mongo")
public class AuthorMongoMapper {

    private static SpringDataMongoAuthorRepository repo;

    @Autowired
    public AuthorMongoMapper(SpringDataMongoAuthorRepository injectedRepo) {
        AuthorMongoMapper.repo = injectedRepo;
    }

    public static Author toDomain(AuthorMongo entity) {
        if (entity == null) return null;

        Name authorName = entity.getName() != null ? new Name(entity.getName().getName()) : null;
        Bio authorBio = entity.getBio() != null ? new Bio(entity.getBio().toString()) : null;

        Author author = new Author(
                authorName,
                authorBio,
                entity.getAuthorNumber() != null ? entity.getAuthorNumber().toString() : null
        );
        author.setAuthorNumber(Long.valueOf(entity.getAuthorNumber().toString()));

        return author;
    }

    public static AuthorMongo toMongo(Author domain) {
        if (repo != null) {
            Optional<AuthorMongo> existing = repo.findByAuthorNumber(String.valueOf(domain.getAuthorNumber()));
            if (existing.isPresent()) {
                return existing.get();
            }
        }
        final var name = new NameEmbeddable(domain.getName());
        final var bio = new BioMongo(domain.getBio());
        final var mongo = new AuthorMongo(name, bio, domain.getPhoto());// safe if null (for creates)
        // If domain exposes version, set it; MONGO @Version will manage increments.
        return mongo;
    }
}
