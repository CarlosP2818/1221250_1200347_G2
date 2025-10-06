package pt.psoft.g1.psoftg1.authormanagement.infrastructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;

@Document(collection = "author")
@Getter
@Setter
public class AuthorMongo {

    @Id
    private Long authorNumber;

    @Version
    private long version;

    @Field("name")
    private Name name;

    @Field("bio")
    private Bio bio;

    public AuthorMongo() {
    }

    public AuthorMongo(Bio bio, Name name, long version) {
        this.bio = bio;
        this.name = name;
        this.version = version;
    }
}

