package pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.shared.infrastructure.persistence.jpa.NameEmbeddable;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.services.Base65IdGenerator;

@Getter
@Setter
@Document(collection = "author")
public class AuthorMongo {

    @Id
    private String authorNumber;

    @Version
    private long version;

    @Field("name")
    private NameEmbeddable name;

    @Field("bio")
    private BioMongo bio;

    private Photo photo;

    public AuthorMongo() {
    }

    public AuthorMongo(NameEmbeddable name, BioMongo bio, Photo photo) {
        this.authorNumber = Base65IdGenerator.generateId();
        this.name = name;
        this.bio = bio;
        this.photo = photo;
    }
}

