package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.mongo.ReaderDetailsMongo;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoReaderDetailsRepository extends MongoRepository<ReaderDetailsMongo, String> {

    Optional<ReaderDetailsMongo> findByReaderNumber(String readerNumber);

    List<ReaderDetailsMongo> findByPhoneNumber(String phoneNumber);

    Optional<ReaderDetailsMongo> findByReader_Username(String username);

    Optional<ReaderDetailsMongo> findByReader_Id(Long userId);

}
