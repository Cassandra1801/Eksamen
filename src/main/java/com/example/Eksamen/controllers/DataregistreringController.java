package com.example.Eksamen.controllers;

import com.example.Eksamen.models.*;
import com.example.Eksamen.services.DataregistreringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DataregistreringController {

    private final DataregistreringService dataregistreringService;

    public DataregistreringController(DataregistreringService dataregistreringService) {
        this.dataregistreringService = dataregistreringService;
    }

    /// GetMapping for frontsiden hos dataregistreringen med parametrer til søgningen
    @GetMapping("/dataregistrering")
    public String dataregistrering(@RequestParam(required = false) String sogning,
                                   Model model) {

        List<Lejeaftale> lejeaftaleListe = dataregistreringService.findFiltreredeLejeaftaler(sogning);

        model.addAttribute("lejeaftaleListe", lejeaftaleListe);
        model.addAttribute("sogning", sogning);

        return "dataregistrering/dataregistrering";
    }



    // Viser formularen til oprettelse af lejeaftale
    @GetMapping("/lejeaftale/opret")
    public String visOpretLejeaftaleForm(Model model) {
        // Tomme objekter som kan udfyldes
        model.addAttribute("lejeaftale", new Lejeaftale());
        model.addAttribute("kunde", new Kunde());

        return "dataregistrering/opret-lejeaftale";
    }

    // Modtager formularen og opretter lejeaftalen
    @PostMapping("/lejeaftale/opret")
    public String opretLejeaftale(
            // Bruger formen til at udfylde objekterne
            @ModelAttribute Lejeaftale lejeaftale,
            @ModelAttribute Kunde kunde,
            Model model
    ) {
        try {
            dataregistreringService.registrerLejeaftale(lejeaftale, kunde);

            // Redirect forhindrer dobbelt-oprettelse ved refresh
            return "redirect:/lejeaftale/opret?success";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage() + "   -   Fejl ved oprettelse af lejeaftale");

            return "dataregistrering/opret-lejeaftale";
        }
    }

    // Viser formularen til oprettelse af ny bil
    @GetMapping("/bil/opret")
    public String visOpretBilForm(Model model) {
        model.addAttribute("bilForm", new BilForm());

        return "dataregistrering/opret-bil";
    }

    // Modtager formularen og opretter bilen
    @PostMapping("/bil/opret")
    public String opretBil(@ModelAttribute BilForm bilForm, Model model) {
        // Bruger BilForm for at data kan gemmes inde i et objekt ligegyldigt om det er en LIMITED eller en UNLIMITED bil

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

                /* Unlimited KRÆVER en aftaleperiode. Hvis feltet er tomt, er getAftaltePeriodeIMaaneder() null,
                    og auto-unboxing til konstruktørens int ville kaste NullPointerException.
                    Derfor tjekkes der eksplicit, så fejlen i stedet bliver en forståelig besked til brugeren. */
                if (bilForm.getAftaltePeriodeIMaaneder() == null) {
                    throw new IllegalArgumentException(
                            "Aftaleperiode skal udfyldes for en Unlimited-bil");
                }

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
                        bilForm.getAftaltePeriodeIMaaneder()
                );
            } else {

                // For at understøtte fremtidige ændringer der kan give fejl
                throw new IllegalArgumentException ("Ukendt biltype");
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
