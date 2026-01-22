package ch.css.vibe.controller;

import ch.css.vibe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager;

    public AuthController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

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

            // Auto-login
            var authToken = new UsernamePasswordAuthenticationToken(username, password);
            var auth = authManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/web/";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
