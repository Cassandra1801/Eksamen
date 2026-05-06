package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.services.ServiceSkadesrapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


@Controller
public class ControllerSkadesrapport {

    @Autowired
    private ServiceSkadesrapport service;

    // Modtager skadedata fra formularen i frontend
    // @ModelAttribute binder automatisk formularens felter til Skadesrapport objektet
    @PostMapping("/skadesrapport/opret")
    public String opretSkade(@ModelAttribute Skadesrapport skade) {
        service.opretSkade(skade);           // Sender skaden videre til Service
        return "redirect:/skadesrapport";    // Sender brugeren tilbage til skadesrapport siden
    }

}