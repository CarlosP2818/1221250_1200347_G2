package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.persistence.mongo.AuthorMongoMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo.GenreMongoMapper;

@Component

@Profile("mongo")
public class BookMongoMapper {

    public Book toDomain(BookMongo mongo) {
        if (mongo == null) return null;

        Book domain = new Book();
        mongo.setTitle(mongo.getTitle());
        mongo.setIsbn(mongo.getIsbn());
        mongo.getDescription();
        GenreMongoMapper.toDomain(mongo.getGenre());
        mongo.setAuthors(mongo.getAuthors());
        mongo.getPhoto();

        return domain;
    }

    public BookMongo toMongo(Book domain) {
        if (domain == null) return null;

        BookMongo mongo = new BookMongo();
        domain.getTitle();
        domain.getIsbn();
        domain.getDescription();
        GenreJpaMapper.toJpa(domain.getGenre());
        domain.getAuthors().stream().map(AuthorJpaMapper::toJpa).toList();
        domain.getPhoto();
        return mongo;
    }
}