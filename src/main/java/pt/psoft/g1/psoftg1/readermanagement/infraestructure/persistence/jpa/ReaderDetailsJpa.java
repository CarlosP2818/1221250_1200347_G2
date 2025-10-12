package pt.psoft.g1.psoftg1.readermanagement.infraestructure.persistence.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.GenreJpaMapper;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.infrastructure.persistence.jpa.EntityWithPhotoEmbeddable;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "READER_DETAILS")
public class ReaderDetailsJpa extends EntityWithPhotoEmbeddable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Getter
    @Setter
    @OneToOne
    private Reader reader;

    @Getter
    private ReaderNumberEmbedded readerNumber;

    @Embedded
    @Getter
    private BirthDateEmbedded birthDate;

    @Embedded
    @Getter
    private PhoneNumberEmbedded phoneNumber;

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
    private List<GenreJpa> interestList;

    public ReaderDetailsJpa(int readerNumber, Reader reader, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, String photoURI, List<Genre> interestList) {
        if(reader == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        setReader(reader);
        setReaderNumber(new ReaderNumberEmbedded(readerNumber));
        setPhoneNumber(new PhoneNumberEmbedded(phoneNumber));
        setBirthDate(new BirthDateEmbedded(birthDate));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(true);

        setPhotoInternal(photoURI);
        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
        if(interestList == null) {
            setInterestList(new ArrayList<GenreJpa>());
        }else {
            setInterestList(interestList.stream().map(GenreJpaMapper::toJpa).toList());
        }
    }

    private void setPhoneNumber(PhoneNumberEmbedded number) {
        if(number != null) {
            this.phoneNumber = number;
        }
    }

    private void setReaderNumber(ReaderNumberEmbedded readerNumber) {
        if(readerNumber != null) {
            this.readerNumber = readerNumber;
        }
    }

    private void setBirthDate(BirthDateEmbedded date) {
        if(date != null) {
            this.birthDate = date;
        }
    }

    public List<Genre> getInterestList() {
        return interestList.stream().map(GenreJpaMapper::toDomain).toList();
    }

    protected ReaderDetailsJpa() {
        // for ORM only
    }
}
