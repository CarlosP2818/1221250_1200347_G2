package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.persistence.mongo;

import com.mongodb.lang.Nullable;
import jakarta.validation.constraints.Size;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class DescriptionMongo {

    private static final int DESC_MAX_LENGTH = 4096;

    @Size(max = DESC_MAX_LENGTH)
    private String description;

    public DescriptionMongo(String description) {
        setDescription(description);
    }

    protected DescriptionMongo() {
    }

    public void setDescription(@Nullable String description) {
        if(description == null || description.isBlank()) {
            this.description = null;
        } else if(description.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException("Description has a maximum of 4096 characters");
        } else {
            this.description = StringUtilsCustom.sanitizeHtml(description);
        }
    }

    @Override
    public String toString() {
        return this.description;
    }
}

