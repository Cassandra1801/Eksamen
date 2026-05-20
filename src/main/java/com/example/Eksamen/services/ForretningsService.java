package com.example.Eksamen.services;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.repositories.BilRepository;
import com.example.Eksamen.repositories.LejeaftaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForretningsService {

    private final BilRepository bilRepository;
    private final LejeaftaleRepository lejeaftaleRepository;

    public ForretningsService(BilRepository bilRepository, LejeaftaleRepository lejeaftaleRepository) {
        this.bilRepository = bilRepository;
        this.lejeaftaleRepository = lejeaftaleRepository;
    }

    // Henter antallet af biler i systemet
    public int totalAntalBiler() {
        return bilRepository.antalBiler();
    }

    // Henter antallet af biler der er udlejet
    public int totalAntalUdlejet() {
        return bilRepository.antalMedStatus(BilStatus.UDLEJET);
    }

    // Henter antallet af biler der er ledige
    public int totalAntalLedige() {
        return bilRepository.antalMedStatus(BilStatus.LEDIG);
    }

    // Henter samlet månedlig pris for aktive lejeaftaler
    public double sammenlagtPris() {
        return lejeaftaleRepository.sammenlagtPrisUdlejede();
    }

    public List<Bil> findFiltreredeBiler(boolean limited, boolean unlimited, String maerke) {
        return bilRepository.findFiltreredeBiler(limited, unlimited, maerke);
    }

    public List<String> findAlleMaerker() {
        return bilRepository.findAlleMaerker();
    }
}
