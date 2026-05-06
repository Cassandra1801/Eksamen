package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class ControllerHjem {

    @GetMapping("/")
    public String forside() {
        return "forside";
    }

    @GetMapping("/dataregistrering")
    public String dataregistrering() {
        return "dataregistrering";
    }

    @GetMapping("/skadesrapport")
    public String skadesrapport(Model model) {
        model.addAttribute("skadesrapport", new Skadesrapport());
        return "skade/registrer-skade";
    }

    @GetMapping("/forretning")
    public String forretning() {
        return "forretning";
    }
}
