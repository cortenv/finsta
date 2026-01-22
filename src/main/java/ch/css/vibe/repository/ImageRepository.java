package ch.css.vibe.repository;

import ch.css.vibe.entity.ImageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<ImageEntity, String> { }
