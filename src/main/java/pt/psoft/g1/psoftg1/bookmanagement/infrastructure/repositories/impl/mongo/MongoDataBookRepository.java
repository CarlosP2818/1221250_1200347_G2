package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;

import java.util.List;
import java.util.Optional;

public interface MongoDataBookRepository {

    List<BookMongo> findByGenre(String genre);

    List<BookMongo> findByTitle(String title);

    List<BookMongo> findByAuthorName(String authorName);

    Optional<BookMongo> findByIsbn(String isbn);

    List<BookMongo> findBooksByAuthorNumber(Long authorNumber);

    BookMongo save(BookMongo book);

    Iterable<BookMongo> findAll();

    void delete(BookMongo book);
}
