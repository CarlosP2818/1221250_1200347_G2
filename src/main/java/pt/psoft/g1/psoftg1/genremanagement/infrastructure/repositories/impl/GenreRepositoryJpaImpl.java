package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("sql")
public class GenreRepositoryJpaImpl implements GenreRepository {
    private final SpringDataGenreRepository jpaRepository;

    @Override
    public Iterable<Genre> findAll() {
        return jpaRepository.findAll().stream().map(GenreJpaMapper::toDomain).toList();
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        return jpaRepository.findByGenre(genreName).map(GenreJpaMapper::toDomain);
    }

    @Override
    public Genre save(Genre genre) {
        final var saved = jpaRepository.save(GenreJpaMapper.toJpa(genre));
        return GenreJpaMapper.toDomain(saved);
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        throw new UnsupportedOperationException("findTop5GenreByBookCount not implemented in the JPA adapter yet");
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        throw new UnsupportedOperationException("getAverageLendingsInMonth not implemented in the JPA adapter yet");
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        throw new UnsupportedOperationException("getLendingsPerMonthLastYearByGenre not implemented in the JPA adapter yet");
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        throw new UnsupportedOperationException("getLendingsAverageDurationPerMonth not implemented in the JPA adapter yet");
    }

    @Override
    public void delete(Genre genre) {
        jpaRepository.deleteByGenre(genre.getGenre());
    }
}
