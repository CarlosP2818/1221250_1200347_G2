package pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.mongo;

import jakarta.persistence.Basic;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.mongo.GenreMongo;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mongo.GenreMongoMapper;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.persistence.mongo.ReaderMongo;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mongo.ReaderMongoMapper;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "readerDetails")
public class ReaderDetailsMongo {

    @Id
    private String id;

    @Getter
    @Setter
    private ReaderMongo reader;

    @Getter
    private ReaderNumberMongo readerNumber;

    @Getter
    private BirthDateMongo birthDate;

    @Getter
    private PhoneNumberMongo phoneNumber;

    @Setter
    @Getter
    @Basic
    private boolean gdprConsent;

    @Setter
    @Basic
    @Getter
    private boolean marketingConsent;

    @Setter
    @Basic
    @Getter
    private boolean thirdPartySharingConsent;

    @Version
    @Getter
    private Long version;

    @Setter
    @ManyToMany
    private List<GenreMongo> interestList;

    @Getter
    private Photo photo;

    public ReaderDetailsMongo(int readerNumber, Reader reader, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, String photoURI, List<Genre> interestList) {
        if(reader == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        setReader(ReaderMongoMapper.toMongo(reader));
        setReaderNumber(new ReaderNumberMongo(readerNumber));
        setPhoneNumber(new PhoneNumberMongo(phoneNumber));
        setBirthDate(new BirthDateMongo(birthDate));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(true);

        setPhotoInternal(photoURI);
        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
        if(interestList == null) {
            setInterestList(new ArrayList<GenreMongo>());
        }else {
            setInterestList(interestList.stream().map(GenreMongoMapper::toMongo).toList());
        }
    }

    private void setReaderNumber(ReaderNumberMongo readerNumber) {
        if(readerNumber != null) {
            this.readerNumber = readerNumber;
        }
    }

    private void setPhoneNumber(PhoneNumberMongo phoneNumber) {
        if(phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
    }

    private void setBirthDate(BirthDateMongo birthDateMongo) {
        if(birthDateMongo != null) {
            this.birthDate = birthDateMongo;
        }
    }

    protected void setPhotoInternal(String photoURI) {
        if (photoURI == null) {
            this.photo = null;
        } else {
            try {
                //If the Path object instantiation succeeds, it means that we have a valid Path
                this.photo = new Photo(Path.of(photoURI));
            } catch (InvalidPathException e) {
                //For some reason it failed, let's set to null to avoid invalid references to photos
                this.photo = null;
            }
        }
    }

    public List<Genre> getInterestList() {
        return interestList.stream().map(GenreMongoMapper::toDomain).toList();
    }

    protected ReaderDetailsMongo() {
        // for ORM only
    }


}
