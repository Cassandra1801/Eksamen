package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HjemController {

    @GetMapping("/")
    public String forside() {
        return "forside";
    }

    @GetMapping("/opret-skade")
    public String opretSkade() {
        return "skade/opret-skade";
    }

    @GetMapping("/registrer-skadesrapport")
    public String skadesrapport(Model model) {
        model.addAttribute("registrerSkadesrapport", new Skadesrapport());
        return "skade/registrer-skade";
    }
}
