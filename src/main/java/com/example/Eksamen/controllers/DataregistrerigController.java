package com.example.Eksamen.controllers;

import com.example.Eksamen.models.*;
import com.example.Eksamen.services.DataregistreringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DataregistrerigController {

    private final DataregistreringService dataregistreringService;

    public DataregistrerigController(DataregistreringService dataregistreringService) {
        this.dataregistreringService = dataregistreringService;
    }

    @GetMapping("/dataregistrering")
    public String dataregistrering(@RequestParam(required = false) String sogning,
                                   Model model) {

        List<Lejeaftale> lejeaftaleListe = dataregistreringService.findFiltreredeLejeaftaler(sogning);

        model.addAttribute("lejeaftaleListe", lejeaftaleListe);
        model.addAttribute("sogning", sogning);

        return "/dataregistrering/dataregistrering";
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
            dataregistreringService.registrerLejeaftale(lejeaftale, kunde);

            //Redirect for at registrering ikke sker flere gange, ?success er til success besked
            return "redirect:/lejeaftale/opret?success";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage() + "   -   Fejl ved oprettelse af lejeaftale");

            return "/dataregistrering/opret-lejeaftale";
        }
    }

    /// GetMapping for form af oprettelse af ny bil
    @GetMapping("/bil/opret")
    public String visOpretBilForm(Model model) {
        model.addAttribute("bilForm", new BilForm());

        return "dataregistrering/opret-bil";
    }

    /// PostMapping for oprettelse af ny bil
    @PostMapping("/bil/opret")
    public String opretBil(@ModelAttribute BilForm bilForm, Model model) {
        try {
            Bil bil;

            if (bilForm.getAbonnementsType().equalsIgnoreCase("LIMITED")) {
                bil = new LimitedBil(
                        bilForm.getVognnummer(),
                        bilForm.getStelnummer(),
                        bilForm.getMaerke(),
                        bilForm.getModel(),
                        bilForm.getUdstyrsniveau(),
                        bilForm.getStaalpris(),
                        bilForm.getRegAfgift(),
                        bilForm.getCo2Udledning(),
                        bilForm.getFarve(),
                        bilForm.getStatus()
                );
            } else if (bilForm.getAbonnementsType().equalsIgnoreCase("UNLIMITED")) {
                bil = new UnlimitedBil(
                        bilForm.getVognnummer(),
                        bilForm.getStelnummer(),
                        bilForm.getMaerke(),
                        bilForm.getModel(),
                        bilForm.getUdstyrsniveau(),
                        bilForm.getStaalpris(),
                        bilForm.getRegAfgift(),
                        bilForm.getCo2Udledning(),
                        bilForm.getFarve(),
                        bilForm.getStatus(),
                        bilForm.getAftalePeriodeIMaaneder()
                );
            } else {
                throw new IllegalArgumentException("Fejl bil type indtastet i bil opretform");
            }

            dataregistreringService.registrerNyBil(bil);

            return "redirect:/bil/opret?success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("bilForm", bilForm);

            return "dataregistrering/opret-bil";
        }
    }






}
