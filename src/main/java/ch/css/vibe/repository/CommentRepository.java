package ch.css.vibe.repository;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByImageOrderByIdAsc(ImageEntity image);
}
