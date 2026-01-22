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

    // Upload image
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description
    ) throws IOException {
        ImageEntity savedImage = imageService.saveImage(image, description);
        return ResponseEntity.ok(savedImage.getId()); // return String ID
    }

    // Get image by ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        ImageEntity image = imageService.getImage(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + image.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(image.getData());
    }

    // Add comment
    @PostMapping("/{id}/comment")
    public ResponseEntity<String> addComment(
            @PathVariable String id,
            @RequestParam String text,
            @RequestParam(defaultValue = "Anonymous") String author
    ) {
        // Correct constructor: imageId, text, author
        ch.css.vibe.entity.Comment comment = new ch.css.vibe.entity.Comment(id, text, author);
        imageService.addComment(id, comment);
        return ResponseEntity.ok("Comment added");
    }

}
