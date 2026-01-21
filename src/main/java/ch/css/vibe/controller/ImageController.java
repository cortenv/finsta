package ch.css.vibe.controller;

import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.service.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Long> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description
    ) throws IOException {

        ImageEntity savedImage = imageService.saveImage(image, description);
        return ResponseEntity.ok(savedImage.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageEntity image = imageService.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + image.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(image.getData());
    }
}
