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

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String id) {

        User user = userRepository.findById(id).orElseThrow();

        if (user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", user.getProfileImageType())
                .body(user.getProfileImage());
    }
}
