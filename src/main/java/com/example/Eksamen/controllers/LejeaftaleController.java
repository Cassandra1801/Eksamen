package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Kunde;
import com.example.Eksamen.models.Lejeaftale;
import com.example.Eksamen.services.LejeaftaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LejeaftaleController {

    private final LejeaftaleService lejeaftaleService;

    public LejeaftaleController (LejeaftaleService lejeaftaleService) {
        this.lejeaftaleService = lejeaftaleService;
    }

    /// GetMapping for oprettelse af lejeaftale form
    @GetMapping("/lejeaftale/opret")
    public String visOpretLejeaftaleForm(Model model) {
        model.addAttribute("lejeaftale", new Lejeaftale()); //Tomt objekt som kan udfyldes
        model.addAttribute("kunde", new Kunde()); //Tomt objekt som kan udfyldes

        return "/dataregistrering/opret-lejeaftale";
    }

    /// PostMapping for oprettelse af lejeaftale form
    @PostMapping("/lejeaftale/opret")
    public String opretLejeaftale(
            @ModelAttribute Lejeaftale lejeaftale, //Brug formen til at fylde dette object
            @ModelAttribute Kunde kunde, //Brug formen til at fylde dette object
            Model model
    ) {
        try {
            lejeaftaleService.registrerLejeaftale(lejeaftale, kunde);

            //Redirect for at registrering ikke sker flere gange, ?success er til success besked
            return "redirect:/lejeaftale/opret?success";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage() + "   -   Fejl ved oprettelse af lejeaftale");

            return "/dataregistrering/opret-lejeaftale";
        }
    }





}
