package ch.css.vibe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String rootRedirect() {
        // Redirect the default "/" to the register page
        return "redirect:/auth/register";
    }
}
