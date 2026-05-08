package com.example.Eksamen.services;

import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.models.Kunde;
import com.example.Eksamen.models.Lejeaftale;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.repositories.KundeRepository;
import com.example.Eksamen.repositories.LejeaftaleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LejeaftaleService {

    private final LejeaftaleRepository lejeaftaleRepository;
    private final KundeRepository kundeRepository;
    private final BilRepository bilRepository;

    public LejeaftaleService (
            LejeaftaleRepository lejeaftaleRepository,
            KundeRepository kundeRepository,
            BilRepository bilRepository
    ) {
        this.lejeaftaleRepository = lejeaftaleRepository;
        this.kundeRepository = kundeRepository;
        this.bilRepository = bilRepository;
    }


    /// Funktion som er logikken bag registreringen af lejeaftaler
    public void registrerLejeaftale (Lejeaftale lejeaftale, Kunde kunde){

        //Definerer vognnummeret for lejeaftalen
        int vognnummer = lejeaftale.getVognnummer();

        //Exception vis bilen ikke eksisterer
        if (!bilRepository.eksistererVognnummeret(vognnummer)) {
            throw new IllegalArgumentException("Bilen findes ikke");
        }

        //Exception vis bilen ikke er ledig
        if (!bilRepository.erBilenLedig(vognnummer)) {
            throw new IllegalArgumentException("Bilen er ikke ledig");
        }

        //Container for potentielt eksisterende kunde fundet med mail (schrödingers cat xD)
        Optional<Kunde> potentielKunde = kundeRepository.findMedEmail(kunde.getEmail());

        int kundeId;

        //Tjekker om containeren indeholder en kunde
        if (potentielKunde.isPresent()) {
            kundeId = potentielKunde.get().getKundeId(); //Er der en kunde, finder vi kundeId
        } else {
            kundeId = kundeRepository.opretKunde(kunde); //Er der ingen kunde, opretter vi en nykunde
        }

        //Indsætter kundeID ind i lejeaftale objektet
        lejeaftale.setKundeId(kundeId);

        //Opretter lejeaftalen
        lejeaftaleRepository.opretLejeaftale(lejeaftale);

        //Sætter bilens status til udlejet
        bilRepository.opdaterStatus(vognnummer, BilStatus.UDLEJET);

    }

}
