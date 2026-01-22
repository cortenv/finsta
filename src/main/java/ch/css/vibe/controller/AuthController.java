package ch.css.vibe.controller;

import ch.css.vibe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER PAGE
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    // REGISTER ACTION
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile profileImage,
            Model model
    ) {
        try {
            byte[] imageBytes = null;
            String imageType = null;

            if (profileImage != null && !profileImage.isEmpty()) {
                imageBytes = profileImage.getBytes();
                imageType = profileImage.getContentType();
            }

            userService.register(username, password, imageBytes, imageType);

            // ✅ Redirect after successful register
            return "redirect:/web/";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
