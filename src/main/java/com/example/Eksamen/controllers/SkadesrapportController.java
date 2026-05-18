package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.services.SkadesrapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Controller
public class SkadesrapportController {

    @Autowired
    private SkadesrapportService service;

    // Modtager data fra side 1 og viser side 2
    @PostMapping("/registrer-skadesrapport")
    public String visSkadefelter(@RequestParam String vognnummer,
                                 @RequestParam int lejeaftaleId,
                                 @RequestParam String medarbejderId,
                                 @RequestParam String dato,
                                 @RequestParam int antalSkader,
                                 Model model) {
        model.addAttribute("vognnummer", vognnummer);
        model.addAttribute("lejeaftaleId", lejeaftaleId);
        model.addAttribute("medarbejderId", medarbejderId);
        model.addAttribute("dato", dato);
        model.addAttribute("antalSkader", antalSkader);
        return "skade/registrer-skade"; // viser side 2
    }

    // Modtager skader fra side 2 og gemmer i databasen
    @PostMapping("/gem-skadesrapport")
    public String gemSkader(@RequestParam String vognnummer,
                            @RequestParam int lejeaftaleId,
                            @RequestParam String medarbejderId,
                            @RequestParam String dato,
                            @RequestParam List<String> beskrivelse,
                            @RequestParam List<BigDecimal> pris,
                            Model model) {

        try {
            for (int i = 0; i < beskrivelse.size(); i++) {
                Skadesrapport skade = new Skadesrapport();
                skade.setVognnummer(vognnummer);
                skade.setLejeaftaleId(lejeaftaleId);
                skade.setMedarbejderId(medarbejderId);
                skade.setDato(LocalDate.parse(dato));
                skade.setBeskrivelse(beskrivelse.get(i));
                skade.setPris(pris.get(i));
                service.opretSkade(skade);
            }

            // Når alle skader er oprettet, ændres bilens status til SKADET
            service.afslutSkaderegistrering(vognnummer);

            // gem hver skade i databasen
            return "redirect:/opret-skade?success=true";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "skade/opret-skade";
        }
    }
}

