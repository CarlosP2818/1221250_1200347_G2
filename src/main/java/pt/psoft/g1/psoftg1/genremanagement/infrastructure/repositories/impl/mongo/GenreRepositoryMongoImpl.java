package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.SpringDataGenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("mongo")
public class GenreRepositoryMongoImpl implements GenreRepository {

    private final SpringDataMongoGenreRepository mongoRepository;

    @Override
    public Iterable<Genre> findAll() {
        return mongoRepository.findAll().stream().map(GenreMongoMapper::toDomain).toList();
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        return mongoRepository.findByGenre(genreName).map(GenreMongoMapper::toDomain);
    }

    @Override
    public Genre save(Genre genre) {
        final var saved = mongoRepository.save(GenreMongoMapper.toMongo(genre));
        return GenreMongoMapper.toDomain(saved);
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        throw new UnsupportedOperationException("getLendingsAverageDurationPerMonth not implemented in the Mongo adapter yet");
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        throw new UnsupportedOperationException("getLendingsAverageDurationPerMonth not implemented in the Mongo adapter yet");
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        throw new UnsupportedOperationException("getLendingsAverageDurationPerMonth not implemented in the Mongo adapter yet");
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        throw new UnsupportedOperationException("getLendingsAverageDurationPerMonth not implemented in the Mongo adapter yet");
    }

    @Override
    public void delete(Genre genre) {
        mongoRepository.deleteByGenre(genre.getGenre());
    }
}
