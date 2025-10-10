package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo.BookMongo;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("mongo")
@RequiredArgsConstructor
public class BookRepositoryMongoImpl implements BookRepository {

    private final MongoTemplate mongoTemplate;
    private final BookMongoMapper mapper;

    @Override
    public List<Book> findByGenre(String genre) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genre.genre").regex(genre, "i"));
        List<BookMongo> results = mongoTemplate.find(query, BookMongo.class);
        return results.stream().map(mapper::toDomain).collect(Collectors.toList());    }

    @Override
    public List<Book> findByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title.title").regex(title, "i"));
        List<BookMongo> results = mongoTemplate.find(query, BookMongo.class);
        return results.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authors.name").regex(authorName, "i"));
        List<BookMongo> results = mongoTemplate.find(query, BookMongo.class);
        return results.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("isbn.isbn").is(isbn));
        BookMongo doc = mongoTemplate.findOne(query, BookMongo.class);
        return Optional.ofNullable(doc).map(mapper::toDomain);
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gt(oneYearAgo)),
                Aggregation.group("bookId").count().as("lendCount"),
                Aggregation.sort(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "lendCount")),
                Aggregation.limit(5),
                Aggregation.lookup("book", "_id", "bookId", "book"),
                Aggregation.unwind("book"),
                Aggregation.project()
                        .and("book").as("book")
                        .and("lendCount").as("count")
        );

        AggregationResults<BookCountDTO> results = mongoTemplate.aggregate(aggregation, "lendings", BookCountDTO.class);
        List<BookCountDTO> dtos = results.getMappedResults();
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authors.authorNumber").is(authorNumber));
        List<BookMongo> results = mongoTemplate.find(query, BookMongo.class);
        return results.stream().map(mapper::toDomain).collect(Collectors.toList());    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        Query mongoQuery = new Query();
        if (query.getTitle() != null && !query.getTitle().isBlank()) {
            mongoQuery.addCriteria(Criteria.where("title.title").regex(query.getTitle(), "i"));
        }
        if (query.getGenre() != null && !query.getGenre().isBlank()) {
            mongoQuery.addCriteria(Criteria.where("genre.genre").regex(query.getGenre(), "i"));
        }
        List<BookMongo> results = mongoTemplate.find(mongoQuery, BookMongo.class);
        return results.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Iterable<Book> findAll() {
        List<BookMongo> docs = mongoTemplate.findAll(BookMongo.class);
        return docs.stream().map(mapper::toDomain).collect(Collectors.toList());    }

    @Override
    public void delete(Book book) {

    }
}
