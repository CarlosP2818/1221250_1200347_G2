package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.jpa.AuthorJpa;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.jpa.AuthorJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.jpa.BookJpa;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.BookJpaMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.jpa.SpringDataBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsJpa;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReaderJpaMapper {
    private static SpringDataReaderRepository repo;

    @Autowired
    public ReaderJpaMapper(SpringDataReaderRepository injectedRepo) {
        ReaderJpaMapper.repo = injectedRepo;
    }

    public static ReaderDetails toDomain(ReaderDetailsJpa jpa) {
        if (jpa == null) return null;

        return new ReaderDetails(
                Integer.parseInt(jpa.getReaderNumber().getReaderNumber().split("/")[1]),
                jpa.getReader(),
                jpa.getBirthDate().getBirthDate().toString(),
                jpa.getPhoneNumber().getPhoneNumber(),
                jpa.isGdprConsent(),
                jpa.isMarketingConsent(),
                jpa.isThirdPartySharingConsent(),
                jpa.getPhoto() != null ? jpa.getPhoto().getPhotoFile() : null,
                jpa.getInterestList()

        );
    }

    public static ReaderDetailsJpa toJpa(ReaderDetails readerDetails) {

        // Preferir devolver um BookJpa já existente (mesmo que ID técnico seja diferente)
        if (repo != null) {
            Optional<ReaderDetailsJpa> existing = repo.findByReaderNumber(readerDetails.getReaderNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        assert readerDetails.getPhoto() != null;
        return new ReaderDetailsJpa(
                Integer.parseInt(readerDetails.getReaderNumber().split("/")[1]),
                readerDetails.getReader(),
                readerDetails.getBirthDate().getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                readerDetails.getPhoneNumber(),
                readerDetails.isGdprConsent(),
                readerDetails.isMarketingConsent(),
                readerDetails.isThirdPartySharingConsent(),
                readerDetails.getPhoto() != null ? readerDetails.getPhoto().getPhotoFile() : null,
                readerDetails.getInterestList()
        );
    }
}
