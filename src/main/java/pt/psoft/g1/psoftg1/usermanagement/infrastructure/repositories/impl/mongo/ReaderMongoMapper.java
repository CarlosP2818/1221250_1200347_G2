package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.jpa.ReaderJpa;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.ReaderMongo;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.UserMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Component
@Profile("mongo")
public class ReaderMongoMapper {
    private static SpringDataUserMongoRepository repo;
    private static SpringDataReaderMongoRepository readerRepo;

    @Autowired
    public ReaderMongoMapper(SpringDataUserMongoRepository injectedRepo, SpringDataReaderMongoRepository injectedReaderRepo) {
        ReaderMongoMapper.repo = injectedRepo;
        ReaderMongoMapper.readerRepo = injectedReaderRepo;
    }

    public static User toDomain(UserMongo mongo) {
        if (mongo == null) return null;
        Reader reader = new Reader(mongo.getUsername(), mongo.getPassword());
        reader.setName(mongo.getName());
        mongo.getAuthorities().forEach(reader::addAuthority);
        return reader;
    }

    public static ReaderMongo toMongo(Reader reader) {
        if (reader == null) return null;

        return readerRepo.findByUsername(reader.getUsername())
                .orElseGet(() -> {
                    ReaderMongo mongo = new ReaderMongo(reader.getUsername(), reader.getPassword());
                    mongo.setName(reader.getName().getName());
                    reader.getAuthorities().forEach(mongo::addAuthority);
                    return mongo;
                });
    }
}
