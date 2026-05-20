package com.example.Eksamen.controllers;

import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.services.SkadesrapportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Controller
public class SkadesrapportController {

    private final SkadesrapportService service;

    // Constructor injection: Spring injicerer SkadesrapportService automatisk,
    // fordi klassen kun har én konstruktør. @Autowired er derfor unødvendig.
    public SkadesrapportController(SkadesrapportService service) {
        this.service = service;
    }

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

            // Henter den samlede pris for alle skader på lejeaftalen
            double totalPris = service.totalPris(lejeaftaleId);

            // Sender total med som query-parameter, så succes-siden kan vise den
            return "redirect:/opret-skade?success=true&total=" + totalPris;

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "skade/opret-skade";
        }
    }
}

