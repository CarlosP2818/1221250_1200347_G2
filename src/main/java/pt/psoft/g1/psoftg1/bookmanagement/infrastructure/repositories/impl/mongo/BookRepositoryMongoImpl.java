package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class BookRepositoryMongoImpl implements BookRepository {

    private final SpringDataMongoBookRepository mongoRepository;

    private final EntityManager em;

    @Override
    public List<Book> findByGenre(String genre) {
        return mongoRepository.findByGenre_GenreContaining(genre)
                .stream().map(BookMongoMapper::toDomain).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return List.of();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return mongoRepository.findByIsbn_Isbn(isbn)
                .map(BookMongoMapper::toDomain);
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        // To do
        return new PageImpl<>(List.of(), pageable, 0);
    }

    @Override
    public List<Book> findBooksByAuthorNumber(String authorNumber) {
        return List.of();
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        return List.of();
    }

    @Override
    public Book save(Book book) {
        var saved = mongoRepository.save(BookMongoMapper.toMongo(book));
        return BookMongoMapper.toDomain(saved);
    }

    @Override
    public Iterable<Book> findAll() {
        return mongoRepository.findAll().stream().map(BookMongoMapper::toDomain).toList();
    }

    @Override
    public void delete(Book book) {
        if (book.getIsbn() != null) {
            mongoRepository.deleteById(book.getPk());
        }
    }
}
