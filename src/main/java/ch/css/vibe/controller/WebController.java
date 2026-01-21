package ch.css.vibe.controller;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.repository.CommentRepository;
import ch.css.vibe.repository.ImageRepository;
import ch.css.vibe.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/web")
public class WebController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    public WebController(ImageService imageService, ImageRepository imageRepository,
                         CommentRepository commentRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ImageEntity> images = imageRepository.findAll();
        // Attach comments to each image
        images.forEach(img -> img.setComments(commentRepository.findByImageOrderByIdAsc(img)));
        model.addAttribute("images", images);
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile file,
                         @RequestParam("description") String description) throws IOException {
        imageService.saveImage(file, description);
        return "redirect:/web/";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(id).getData();
    }

    @PostMapping("/comment/{imageId}")
    public String addComment(@PathVariable Long imageId,
                             @RequestParam("text") String text,
                             @RequestParam(value = "author", defaultValue = "Anonymous") String author) {
        ImageEntity image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        Comment comment = new Comment(image, text, author);
        commentRepository.save(comment);
        return "redirect:/web/";
    }
}
