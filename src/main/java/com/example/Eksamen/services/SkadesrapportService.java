package com.example.Eksamen.services;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.repositories.SkadesrapportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkadesrapportService {

    private final SkadesrapportRepository repository;
    private final BilRepository bilRepository;

    /* Constructor injection: Spring leverer begge repositories automatisk,
    så vi slipper for @Autowired og kan markere felterne som final */
    public SkadesrapportService(SkadesrapportRepository repository, BilRepository bilRepository) {
        this.repository = repository;
        this.bilRepository = bilRepository;
    }

    /* Opretter en skade på en bil. Tager imod et Skadesrapport objekt fra Controlleren,
    validerer at bilen er klar til skaderegistrering, og sender derefter skaden videre
    til Repository, som gemmer den i databasen */
    public void opretSkade(Skadesrapport skade) {

        /* Validerer at bilen er tilbageleveret og at lejeperioden er udløbet.
        Hvis ikke, kastes en exception som Controlleren fanger og viser som fejlbesked */
        if (!bilRepository.erKlarTilSkaderegistrering(skade.getVognnummer())) {
            throw new IllegalArgumentException(
                    "Skader kan kun registreres på tilbageleverede biler med udløbet lejeperiode"
            );
        }

        // Sender skaden videre til Repository, som indsætter den i skader-tabellen
        repository.tilfoejSkadeTilRapport(skade);
    }

    /* Opretter alle skader i én samlet service-metode og afslutter derefter
       skaderegistreringen ved at sætte bilens status til SKADET. */
    public void opretSkadesrapport(List<Skadesrapport> skader, String vognnummer) {
        for (Skadesrapport skade : skader) {
            opretSkade(skade);
        }

        afslutSkaderegistrering(vognnummer);
    }

    /* Afslutter skaderegistreringen for en bil ved at sætte status til SKADET.
    Kaldes af Controlleren EFTER at alle skader fra én skadesrapport er oprettet,
    så valideringen i opretSkade() ikke fejler på 2. og 3. skade i samme rapport */
    public void afslutSkaderegistrering(String vognnummer) {
        bilRepository.opdaterStatus(vognnummer, BilStatus.SKADET);
    }

    /* Henter den samlede pris for alle skader på en lejeaftale.
       Sender kaldet videre til repository, som lægger priserne sammen i databasen */
    public double totalPris(int lejeaftaleId) {
        return repository.totalPris(lejeaftaleId);
    }

    // Henter biler der er klar til skaderegistrering
    public List<Bil> hentKlareBiler(){
        return bilRepository.findKlarTilSkaderegistrering();
    }

}
