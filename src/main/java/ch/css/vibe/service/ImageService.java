package ch.css.vibe.service;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.entity.User;
import ch.css.vibe.repository.ImageRepository;
import ch.css.vibe.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    // Save image with user
    public ImageEntity saveImage(MultipartFile file, String description, String userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ImageEntity image = new ImageEntity();
        image.setDescription(description);
        image.setData(file.getBytes());
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setUserId(user.getId());       // userId is String
        image.setUsername(user.getUsername());

        return imageRepository.save(image);
    }

    // Get image by DB ID (Long)
    public ImageEntity getImage(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found: " + id));
    }

    public void addComment(String imageId, Comment comment) {
        ImageEntity image = getImage(imageId);
        image.getComments().add(comment);
        imageRepository.save(image);
    }
}
