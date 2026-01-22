package ch.css.vibe.controller;

import ch.css.vibe.entity.User;
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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam MultipartFile profileImage,
                           Model model) throws Exception {

        if (profileImage.isEmpty()) {
            model.addAttribute("error", "Please select a profile picture");
            return "register";
        }

        try {
            userService.register(
                    username,
                    password,
                    profileImage.getBytes(),
                    profileImage.getContentType()
            );
            return "redirect:/auth/login"; // only redirect on success
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        try {
            User user = userService.login(username, password);
            model.addAttribute("user", user);
            return "redirect:/web/";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/user/{id}/image")
    @ResponseBody
    public org.springframework.http.ResponseEntity<byte[]> getProfileImage(@PathVariable String id) {
        User user = userService.findById(id);
        if (user.getProfileImage() == null) {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
        return org.springframework.http.ResponseEntity.ok()
                .header("Content-Type", user.getProfileImageType())
                .body(user.getProfileImage());
    }
}
