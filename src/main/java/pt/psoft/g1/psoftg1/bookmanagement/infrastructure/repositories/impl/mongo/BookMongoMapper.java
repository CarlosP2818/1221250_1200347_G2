package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo.AuthorMongo;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mongo.AuthorMongoMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.SpringDataBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo.GenreMongoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookMongoMapper {

    private static SpringDataMongoBookRepository repo;

    @Autowired
    public BookMongoMapper(SpringDataMongoBookRepository injectedRepo) {
        BookMongoMapper.repo = injectedRepo;
    }

    public static Book toDomain(BookMongo mongo) {
        if (mongo == null) return null;

        Book book = new Book(
                mongo.getIsbn().getIsbn(),
                mongo.getTitle().getTitle(),
                mongo.getDescription() != null ? mongo.getDescription().toString() : null,
                GenreMongoMapper.toDomain(mongo.getGenre()),
                mongo.getAuthors().stream().map(AuthorMongoMapper::toDomain).toList(),
                mongo.getPhoto() != null ? mongo.getPhoto().getPhotoFile() : null
        );

        book.setVersion(mongo.getVersion());
        return book;
    }

    public static BookMongo toMongo(Book book) {
        if (book == null) return null;

        // Preferir devolver um BookJpa já existente (mesmo que ID técnico seja diferente)
        if (repo != null) {
            Optional<BookMongo> existing = repo.findByIsbn_Isbn(book.getIsbn());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        // Caso contrário, criar um novo
        List<AuthorMongo> authorMongos = book.getAuthors().stream()
                .map(AuthorMongoMapper::toMongo)
                .collect(Collectors.toList());

        return new BookMongo(
                book.getIsbn(),
                book.getTitle().getTitle(),
                book.getDescription() != null ? book.getDescription() : null,
                GenreMongoMapper.toMongo(book.getGenre()),
                authorMongos,
                book.getPhoto()
        );
    }
}
