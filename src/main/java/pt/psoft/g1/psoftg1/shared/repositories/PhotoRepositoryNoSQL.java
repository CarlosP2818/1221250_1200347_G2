package pt.psoft.g1.psoftg1.shared.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;

public class PhotoRepositoryNoSQL implements PhotoRepository {

    private final MongoTemplate mongoTemplate;

    public PhotoRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteByPhotoFile(String photoFile) {
        mongoTemplate.remove(mongoTemplate.findById(photoFile, pt.psoft.g1.psoftg1.shared.model.Photo.class));
    }
}
