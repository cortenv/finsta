package ch.css.vibe.service;

import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public ImageEntity saveImage(MultipartFile file, String description) throws IOException {
        ImageEntity image = new ImageEntity();
        image.setFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setDescription(description);
        image.setData(file.getBytes());

        return imageRepository.save(image);
    }

    public ImageEntity getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }
}
