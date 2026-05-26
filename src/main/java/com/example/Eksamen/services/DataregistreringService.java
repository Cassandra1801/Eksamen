package com.example.Eksamen.services;

import com.example.Eksamen.models.*;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.repositories.KundeRepository;
import com.example.Eksamen.repositories.LejeaftaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DataregistreringService {

    private final LejeaftaleRepository lejeaftaleRepository;
    private final KundeRepository kundeRepository;
    private final BilRepository bilRepository;

    public DataregistreringService(
            LejeaftaleRepository lejeaftaleRepository,
            KundeRepository kundeRepository,
            BilRepository bilRepository
    ) {
        this.lejeaftaleRepository = lejeaftaleRepository;
        this.kundeRepository = kundeRepository;
        this.bilRepository = bilRepository;
    }


    // Registrerer en lejeaftale og samler validering, kundeoprettelse og statusændring ét sted.
    public void registrerLejeaftale (Lejeaftale lejeaftale, Kunde kunde){

        // Finder vognnummeret fra lejeaftalen
        String vognnummer = lejeaftale.getVognnummer();

        // Bilen skal eksistere
        if (!bilRepository.eksistererVognnummeret(vognnummer)) {
            throw new IllegalArgumentException("Bilen findes ikke");
        }

        // Bilen skal være ledig
        if (!bilRepository.erBilenLedig(vognnummer)) {
            throw new IllegalArgumentException("Bilen er ikke ledig");
        }

        Bil bil = bilRepository.findVedVognnummer(vognnummer);

        int lejeperiodeIDage = lejeaftale.getAntalMaaneder() * 30;

        if (bil instanceof UnlimitedBil) {
            if (lejeaftale.getAntalMaaneder() < 3 || lejeaftale.getAntalMaaneder() > 36) {
                throw new IllegalArgumentException("Unlimited lejeaftale skal være mellem 3 og 36 måneder");
            }
        } else {
            if (lejeperiodeIDage > bil.getMaxLejePeriodeIDage()) {
                throw new IllegalArgumentException("Lejeperioden overstiger bilens maksimale lejeperiode");
            }
        }

        // Finder kunden via email, hvis kunden allerede findes
        Optional<Kunde> potentielKunde = kundeRepository.findMedEmail(kunde.getEmail());

        int kundeId;

        // Genbruger eksisterende kunde eller opretter en ny
        if (potentielKunde.isPresent()) {
            kundeId = potentielKunde.get().getKundeId(); //Er der en kunde, finder vi kundeId
        } else {
            kundeId = kundeRepository.opretKunde(kunde); //Er der ingen kunde, opretter vi en ny kunde
        }

        //Indsætter kundeID ind i lejeaftale objektet
        lejeaftale.setKundeId(kundeId);

        //Opretter lejeaftalen
        lejeaftaleRepository.opretLejeaftale(lejeaftale);

        //Sætter bilens status til udlejet
        bilRepository.opdaterStatus(vognnummer, BilStatus.UDLEJET);

    }

    public void registrerNyBil(Bil bil) {

        String vognnummer = bil.getVognnummer();

        if (bilRepository.eksistererVognnummeret(vognnummer)) {
            throw new IllegalArgumentException("Bilen findes allerede i systemet");
        }

        bilRepository.gem(bil);
    }

    public List<Lejeaftale> findFiltreredeLejeaftaler(String sogning) {
        return lejeaftaleRepository.findFiltreredeLejeaftaler(sogning);
    }

    public List<Kunde> findSamsvarendeKunde(List<Lejeaftale> aftaleListe) {
        return kundeRepository.findSamsvarendeKunde(aftaleListe);
    }
}
