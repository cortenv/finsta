package ch.css.vibe.controller;

import ch.css.vibe.entity.User;
import ch.css.vibe.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{id}/image")
    @ResponseBody
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String id) {

        User user = userRepository.findById(id).orElseThrow();

        // ✅ ADD THIS BLOCK HERE
        if (user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", user.getProfileImageType())
                .body(user.getProfileImage());
    }

}
