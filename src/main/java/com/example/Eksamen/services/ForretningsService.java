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

    ///  Henter antallet af biler i systemet som int
    public int totalAntalBiler() {
        return bilRepository.antalBiler();
    }

    /// Henter antallet biler der til udlejning i nuværende tidspunkt som int
    public int totalAntalUdlejet() {
        return bilRepository.antalMedStatus(BilStatus.UDLEJET);
    }

    /// Henter antallet biler der er ledige i nuværende tidspunkt som int
    public int totalAntalLedige() {
        return bilRepository.antalMedStatus(BilStatus.LEDIG);
    }

    /// Henter sammenlagt pris på nuværende udlejede biler
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
