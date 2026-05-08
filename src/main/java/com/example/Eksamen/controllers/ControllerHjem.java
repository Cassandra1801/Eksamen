package com.example.Eksamen.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHjem {

    @GetMapping("/")
    public String forside() {
        return "forside";
    }

    @GetMapping("/dataregistrering")
    public String dataregistrering() {
        return "/dataregistrering/dataregistrering";
    }

    @GetMapping("/skade")
    public String skade() {
        return "skade";
    }

    @GetMapping("/forretning")
    public String forretning() {
        return "forretning";
    }
}
