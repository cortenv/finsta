package ch.css.vibe.controller;

import ch.css.vibe.entity.Comment;
import ch.css.vibe.entity.ImageEntity;
import ch.css.vibe.entity.User;
import ch.css.vibe.repository.CommentRepository;
import ch.css.vibe.repository.ImageRepository;
import ch.css.vibe.repository.UserRepository;
import ch.css.vibe.service.ImageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    public WebController(ImageService imageService,
                         ImageRepository imageRepository,
                         CommentRepository commentRepository,
                         UserRepository userRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ImageEntity> images = imageRepository.findAll();

        for (ImageEntity image : images) {
            // Load comments (convert image.getId() to String)
            image.setComments(commentRepository.findByImageId(image.getId().toString()));

            // Load uploader info
            if (image.getUserId() != null) {
                User user = userRepository.findById(image.getUserId())
                        .orElse(null);
                if (user != null) {
                    image.setUsername(user.getUsername());
                    image.setUserProfileImage(user.getProfileImage());
                    image.setUserProfileImageType(user.getProfileImageType());
                }
            }
        }

        model.addAttribute("images", images);
        return "index";
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile file,
                         @RequestParam("description") String description) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        imageService.saveImage(file, description, user.getId());

        return "redirect:/web/";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(String.valueOf(id)).getData();
    }

    @PostMapping("/comment/{imageId}")
    public String addComment(@PathVariable Long imageId,
                             @RequestParam("text") String text,
                             @RequestParam(value = "author", defaultValue = "Anonymous") String author) {
        // Pass imageId as String to Comment constructor
        Comment comment = new Comment(imageId.toString(), text, author);
        commentRepository.save(comment);
        return "redirect:/web/";
    }
}
