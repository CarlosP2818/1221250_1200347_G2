package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.SpringDataBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BookJpaMapper {

    private static SpringDataBookRepository repo;

    @Autowired
    public BookJpaMapper(SpringDataBookRepository injectedRepo) {
        BookJpaMapper.repo = injectedRepo;
    }

    public static Book toDomain(BookJpa jpa) {
        if (jpa == null) return null;

        return new Book(
                jpa.getIsbn().toString(),
                jpa.getTitle().getTitle(),
                jpa.getDescription() != null ? jpa.getDescription().toString() : null,
                GenreJpaMapper.toDomain(jpa.getGenre()),
                jpa.getAuthors().stream().map(AuthorJpaMapper::toDomain).toList(),
                jpa.getPhoto() != null ? jpa.getPhoto().getPhotoFile() : null
        );
    }

    public static BookJpa toJpa(Book book) {
        if (book == null) return null;

        // Preferir devolver um BookJpa já existente (mesmo que ID técnico seja diferente)
        if (repo != null) {
            Optional<BookJpa> existing = repo.findByIsbn_Isbn(book.getIsbn());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        // Caso contrário, criar um novo
        List<AuthorJpa> authorJpas = book.getAuthors().stream()
                .map(AuthorJpaMapper::toJpa)
                .collect(Collectors.toList());

        return new BookJpa(
                book.getIsbn(),
                book.getTitle().getTitle(),
                book.getDescription() != null ? book.getDescription() : null,
                GenreJpaMapper.toJpa(book.getGenre()),
                authorJpas,
                book.getPhoto()
        );
    }
}
