package ch.css.vibe.controller;

import ch.css.vibe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authManager; // NEW

    public AuthController(UserService userService, AuthenticationManager authManager) {
        this.userService = userService;
        this.authManager = authManager;
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

            // 1️⃣ Create user
            userService.register(username, password, imageBytes, imageType);

            // 2️⃣ Auto-login user
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            var auth = authManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            // 3️⃣ Redirect to web
            return "redirect:/web/";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }


    // LOGIN PAGE
    @GetMapping("/login")
    public String login() {
        return "login"; // This is /auth/login
    }
}
