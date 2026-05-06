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

    @PostMapping("/skadesrapport/opret")
    public String opretSkade(@ModelAttribute Skadesrapport skade) {
        service.opretSkade(skade);
        return "redirect:/skadesrapport";
}
}