package com.example.Eksamen.services;

import com.example.Eksamen.models.Skadesrapport;
import com.example.Eksamen.repositories.SkadesrapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkadesrapportService {

    @Autowired
    private SkadesrapportRepository repository;

    public void opretSkade(Skadesrapport skade) {       // Tager imod et Skadesrapport objekt fra Controller
        repository.tilfoejSkadeTilRapport(skade);       // Sender skaden videre til Repository
                                                        // Repository gemmer den i databasen
    }

    public double totalPris(int aftaleId) {
        return repository.totalPris(aftaleId);
    }
}
