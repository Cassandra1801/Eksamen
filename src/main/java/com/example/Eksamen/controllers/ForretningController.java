package com.example.Eksamen.controllers;


import com.example.Eksamen.models.Bil;
import com.example.Eksamen.services.ForretningsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ForretningController {

    private final ForretningsService forretningsService;

    public ForretningController(ForretningsService forretningsService) {
        this.forretningsService = forretningsService;
    }

    /// Endpoint der henter dashboard for forretning
    @GetMapping("/forretning")
    public String dashboard(
            // Filter parameter
            // (required = false) betyder at det ikke er nødvendigt at udfylde.
            // Bliver sat til false vis ikke er udfyldt
            @RequestParam(required = false) boolean limited,
            @RequestParam(required = false) boolean unlimited,
            @RequestParam(required = false) String maerke,
            Model model
            ) {

        // KPI variabler
        int totalAntalBiler = forretningsService.totalAntalBiler();
        int totalAntalUdlejet = forretningsService.totalAntalUdlejet();
        int totalAntalLedige = forretningsService.totalAntalLedige();
        double sammenlagtPris = forretningsService.sammenlagtPris();

        // Overblik over biler variabler
        List<Bil> bilListe = forretningsService.findFiltreredeBiler(limited, unlimited, maerke);
        List<String> maerkerListe = forretningsService.findAlleMaerker();

        model.addAttribute("totalAntalBiler", totalAntalBiler);
        model.addAttribute("totalAntalUdlejet", totalAntalUdlejet);
        model.addAttribute("totalAntalLedige", totalAntalLedige);
        model.addAttribute("sammenlagtPris", sammenlagtPris);
        model.addAttribute("bilListe", bilListe);
        model.addAttribute("maerkeListe", maerkerListe);

        model.addAttribute("limited", limited);
        model.addAttribute("unlimited", unlimited);
        model.addAttribute("maerke", maerke);

        return "forretning/dashboard";
    }

}
