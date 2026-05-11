package com.example.Eksamen.services;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.repositories.LejeaftaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForretningsService {

    private final BilRepository bilRepository;
    private final LejeaftaleRepository lejeaftaleRepository;

    public ForretningsService(BilRepository bilRepository, LejeaftaleRepository lejeaftaleRepository) {
        this.bilRepository = bilRepository;
        this.lejeaftaleRepository = lejeaftaleRepository;
    }

    /// Henter antallet af biler i systemet som int
    public int totalAntalBiler() {

        int antal = 0;
        List<Bil> liste = bilRepository.findAlle();

        for (Bil b : liste) {
            antal++;
        }

        return antal;
    }

    /// Henter antallet biler der til udlejning i nuværende tidspunkt som int
    public int totalAntalUdlejet() {

        int antalUdlejet = 0;
        List<Bil> liste = bilRepository.findAlle();

        for (Bil b : liste) {
            if (b.getStatus() == BilStatus.UDLEJET) {
                antalUdlejet++;
            }
        }

        return antalUdlejet;
    }

    /// Henter antallet biler der er ledige i nuværende tidspunkt som int
    public int totalAntalLedige() {

        int antalLedige = 0;
        List<Bil> liste = bilRepository.findAlle();

        for (Bil b : liste) {
            if (b.getStatus() == BilStatus.LEDIG) {
                antalLedige++;
            }
        }

        return antalLedige;
    }

    /// Henter sammenlagt pris på nuværende udlejede biler
    public double sammenlagtPris() {
        return lejeaftaleRepository.sammenlagtPrisUdlejede();
    }

    public List<Bil> findAlleLedige() {
        List<Bil> bilerListe = bilRepository.findAlle();
        List<Bil> lejedeBiler = new ArrayList<>();

        for (Bil b : bilerListe) {
            if (b.getStatus() == BilStatus.LEDIG) {
                lejedeBiler.add(b);
            }
        }

        return lejedeBiler;
    }

    public List<Bil> findAlleUdlejede() {
        List<Bil> bilerListe = bilRepository.findAlle();
        List<Bil> udlejedeBiler = new ArrayList<>();

        for (Bil b : bilerListe) {
            if (b.getStatus() == BilStatus.UDLEJET) {
                udlejedeBiler.add(b);
            }
        }

        return udlejedeBiler;
    }


}
