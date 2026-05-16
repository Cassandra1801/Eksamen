package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.repositories.BilRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HjemController {

    private final BilRepository bilRepository;

    public HjemController(BilRepository bilRepository) {
        this.bilRepository = bilRepository;
    }

    @GetMapping("/")
    public String forside() {
        return "forside";
    }

    @GetMapping("/dataregistrering")
    public String dataregistrering() {
        return "/dataregistrering/dataregistrering";
    }

    @GetMapping("/opret-skade")
    public String opretSkade(Model model) {
        model.addAttribute("klareBiler", bilRepository.findKlarTilSkaderegistrering());
        return "skade/opret-skade";
    }

    @GetMapping("/registrer-skadesrapport")
    public String skadesrapport(Model model) {
        model.addAttribute("registrerSkadesrapport", new Skadesrapport());
        return "skade/registrer-skade";
    }
}
