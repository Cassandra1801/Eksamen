package com.example.Eksamen.controllers;


import com.example.Eksamen.models.Bil;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.services.ForretningsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ForretningController {

    private final ForretningsService forretningsService;

    public ForretningController(ForretningsService forretningsService) {
        this.forretningsService = forretningsService;
    }

    @GetMapping("/forretning")
    public String dashboard(Model model) {

        int totalAntalBiler = forretningsService.totalAntalBiler();
        int totalAntalUdlejet = forretningsService.totalAntalUdlejet();
        int totalAntalLedige = forretningsService.totalAntalLedige();
        double sammenlagtPris = forretningsService.sammenlagtPris();
        List<Bil> ledigeBiler = forretningsService.findAlleLedige();
        List<Bil> udlejedeBiler = forretningsService.findAlleUdlejede();


        model.addAttribute("totalAntalBiler", totalAntalBiler);
        model.addAttribute("totalAntalUdlejet", totalAntalUdlejet);
        model.addAttribute("totalAntalLedige", totalAntalLedige);
        model.addAttribute("sammenlagtPris", sammenlagtPris);
        model.addAttribute("ledigeBiler", ledigeBiler);
        model.addAttribute("udlejedeBiler", udlejedeBiler);

        return "forretning/dashboard";
    }

}
