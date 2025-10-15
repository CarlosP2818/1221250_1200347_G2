package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.persistence.mongo;

import com.mongodb.lang.Nullable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Size;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class DescriptionMongo {

    @Transient
    private final int DESC_MAX_LENGTH = 4096;

    @Size(max = 4096)
    private String description;

    public DescriptionMongo(String description) {
        setDescription(description);
    }

    protected DescriptionMongo() {
    }

    public void setDescription(@Nullable String description) {
        if (description == null || description.isBlank()) {
            this.description = null;
        } else if (description.length() > DESC_MAX_LENGTH) {
            throw new IllegalArgumentException("Description has a maximum of 4096 characters");
        } else {
            this.description = StringUtilsCustom.sanitizeHtml(description);
        }
    }

    public String getDescription() {
        return description;
    }

}

