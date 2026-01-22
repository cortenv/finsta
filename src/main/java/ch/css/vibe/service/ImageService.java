package ch.css.vibe.service;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageEntity saveImage(MultipartFile file, String description) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setDescription(description);
        image.setData(file.getBytes());
        image.setContentType(file.getContentType());
        image.setFileName(file.getOriginalFilename());
        image.setComments(new ArrayList<>());
        return imageRepository.save(image);
    }

    public ImageEntity getImage(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public List<ImageEntity> getAllImages() {
        return imageRepository.findAll();
    }

    public ImageEntity addComment(String imageId, Comment comment) {
        ImageEntity image = getImage(imageId);
        if (image.getComments() == null) {
            image.setComments(new ArrayList<>());
        }
        image.getComments().add(comment);
        return imageRepository.save(image);
    }
}
