package com.example.Eksamen.controllers;

import com.example.Eksamen.services.SkadesrapportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HjemController {

    private final SkadesrapportService skadesrapportService;

    public HjemController(SkadesrapportService skadesrapportService) {
        this.skadesrapportService = skadesrapportService;
    }

    @GetMapping("/")
    public String forside() {
        return "forside";
    }

    @GetMapping("/opret-skade")
    public String opretSkade(Model model) {
        model.addAttribute("klareBiler", skadesrapportService.hentKlareBiler());
        return "skade/opret-skade";
    }
}
