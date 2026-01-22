package ch.css.vibe.controller;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.entity.User;
import ch.css.vibe.service.ImageService;
import ch.css.vibe.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;

    public ImageController(ImageService imageService, UserRepository userRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ImageEntity savedImage = imageService.saveImage(image, description, user.getId());

        return ResponseEntity.ok(savedImage.getId().toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        ImageEntity image = imageService.getImage(String.valueOf(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                .body(image.getData());
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> addComment(@PathVariable Long id,
                                             @RequestParam String text,
                                             @RequestParam(defaultValue = "Anonymous") String author) {
        // Convert Long id -> String for Comment
        imageService.addComment(String.valueOf(id), new Comment(id.toString(), text, author));
        return ResponseEntity.ok("Comment added");
    }

}
