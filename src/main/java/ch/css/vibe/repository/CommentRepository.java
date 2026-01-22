package ch.css.vibe.repository;

import ch.css.vibe.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByImageId(String imageId);
}
