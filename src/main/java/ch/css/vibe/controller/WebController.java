package ch.css.vibe.controller;

import ch.css.vibe.entity.ImageEntity;
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

    public WebController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ImageEntity> images = imageRepository.findAll();
        model.addAttribute("images", images);
        return "index";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("image") MultipartFile file,
                         @RequestParam("description") String description,
                         Model model) throws IOException {
        imageService.saveImage(file, description);
        return "redirect:/web/";
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return imageService.getImage(id).getData();
    }

}
