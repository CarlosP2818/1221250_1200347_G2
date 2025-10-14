package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoBookRepository extends MongoRepository<BookMongo, Long>  {

    Optional<BookMongo> findByIsbn_Isbn(String isbn);

    List<BookMongo> findByGenre_GenreContaining(String genre);

}
