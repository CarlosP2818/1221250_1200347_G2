package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpaImpl implements BookRepository {
    private final SpringDataBookRepository jpaRepository;
    private final EntityManager em;

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return jpaRepository.findByIsbn_Isbn(isbn)
                .map(BookJpaMapper::toDomain);
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        return jpaRepository.findTop5BooksLent(oneYearAgo, pageable);
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return jpaRepository.findByGenre_GenreContaining(genre)
                .stream().map(BookJpaMapper::toDomain).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return jpaRepository.findByTitle_TitleContaining(title)
                .stream().map(BookJpaMapper::toDomain).toList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return jpaRepository.findByAuthorName(authorName)
                .stream().map(BookJpaMapper::toDomain).toList();
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        return jpaRepository.findBooksByAuthorNumber(authorNumber)
                .stream().map(BookJpaMapper::toDomain).toList();
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        String title = query.getTitle();
        String genre = query.getGenre();
        String authorName = query.getAuthorName();

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa> cq =
                cb.createQuery(pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa.class);
        final Root<pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa> root = cq.from(pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa.class);
        final Join<pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa, Genre> genreJoin = root.join("genre");
        final Join<pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa, Author> authorJoin = root.join("authors");
        cq.select(root);

        final List<Predicate> where = new ArrayList<>();

        if (StringUtils.hasText(title))
            where.add(cb.like(root.get("title").get("title"), title + "%"));

        if (StringUtils.hasText(genre))
            where.add(cb.like(genreJoin.get("genre"), genre + "%"));

        if (StringUtils.hasText(authorName))
            where.add(cb.like(authorJoin.get("name").get("name"), authorName + "%"));

        cq.where(where.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("title")));

        final TypedQuery<pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList().stream()
                .map(BookJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Book save(Book book) {
        var saved = jpaRepository.save(BookJpaMapper.toJpa(book));
        return BookJpaMapper.toDomain(saved);
    }

    @Override
    public Iterable<Book> findAll() {
        return jpaRepository.findAll().stream().map(BookJpaMapper::toDomain).toList();
    }

    @Override
    public void delete(Book book) {
        if (book.getIsbn() != null) {
            jpaRepository.deleteById(book.getPk());
        }
    }
}
