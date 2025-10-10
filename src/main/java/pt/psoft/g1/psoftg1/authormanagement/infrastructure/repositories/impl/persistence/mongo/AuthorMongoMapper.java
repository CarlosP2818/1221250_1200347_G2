package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.persistence.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Component
@Profile("mongo")
public class AuthorMongoMapper {

    public Author toDomain(AuthorMongo entity) {
        if (entity == null) return null;

        Author author = new Author(
                entity.getName() != null ? entity.getName().toString() : null,
                entity.getBio() != null ? entity.getBio().toString() : null,
                entity.getAuthorNumber() != null ? entity.getAuthorNumber().toString() : null
        );
        author.setAuthorNumber(entity.getAuthorNumber());
        author.setVersion(entity.getVersion());

        return author;
    }

    public static AuthorMongo toEntity(Author domain) {
        if (domain == null) return null;

        AuthorMongo entity = new AuthorMongo(
                domain.getBio() != null ? new Bio(domain.getBio()) : null,
                domain.getName() != null ? new Name(domain.getName()) : null,
                domain.getVersion() != null ? domain.getVersion() : 0L
        );
        entity.setAuthorNumber(domain.getAuthorNumber());
        return entity;
    }
}
