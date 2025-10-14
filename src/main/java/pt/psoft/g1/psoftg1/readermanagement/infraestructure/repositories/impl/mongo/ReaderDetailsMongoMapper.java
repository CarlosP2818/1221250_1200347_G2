package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.mongo.ReaderDetailsMongo;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class ReaderDetailsMongoMapper {
    private static SpringDataMongoReaderDetailsRepository repo;

    @Autowired
    public ReaderDetailsMongoMapper(SpringDataMongoReaderDetailsRepository injectedRepo) {
        ReaderDetailsMongoMapper.repo = injectedRepo;
    }

    public static ReaderDetails toDomain(ReaderDetailsMongo mongo) {
        if (mongo == null) return null;

        return new ReaderDetails(
                Integer.parseInt(mongo.getReaderNumber().getReaderNumber().split("/")[1]),
                new Reader(mongo.getReader().getUsername(),mongo.getReader().getPassword()),
                mongo.getBirthDate().getBirthDate().toString(),
                mongo.getPhoneNumber().getPhoneNumber(),
                mongo.isGdprConsent(),
                mongo.isMarketingConsent(),
                mongo.isThirdPartySharingConsent(),
                mongo.getPhoto() != null ? mongo.getPhoto().getPhotoFile() : null,
                mongo.getInterestList()

        );
    }

    public static ReaderDetailsMongo toMongo(ReaderDetails readerDetails) {
        if (repo != null) {
            Optional<ReaderDetailsMongo> existing = repo.findByReaderNumber(readerDetails.getReaderNumber());
            if (existing.isPresent()) {
                return existing.get();
            }
        }

        assert readerDetails.getPhoto() != null;
        return new ReaderDetailsMongo(
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
