package com.ecomarket.ecomarket.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/api-docs")
    public String apiDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
