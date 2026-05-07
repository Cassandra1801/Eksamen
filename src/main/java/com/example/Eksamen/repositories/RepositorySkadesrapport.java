package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Skadesrapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Fortæller Spring Boot at dette er en Repository klasse
// Spring Boot opretter automatisk et objekt af klassen
@Repository
public class RepositorySkadesrapport {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Beder Spring Boot om at give os en JdbcTemplate
    // vi skal ikke selv oprette den med "new"

        public void tilfoejSkadeTilRapport (Skadesrapport skade) {
            // Metoden tager imod et Skadesrapport objekt

            String sql = "INSERT INTO skader (aftale_id, beskrivelse, pris, dato) VALUES (?,?,?,?)";
            // SQL sætning der indsætter en ny række i databasen
            // ? er pladsholdere som udfyldes nedenfor

            jdbcTemplate.update(sql, skade.getLejeaftaleId(), skade.getBeskrivelse(), skade.getPris(), skade.getDato());
            // udfylder de 4 spørgsmålstegn med data fra skade objektet
            // ? 1 = aftaleId
            // ? 2 = beskrivelse
            // ? 3 = pris
            // ? 4 = dato
        }

    public double totalPris(int aftaleId) {
        String sql = "SELECT SUM(pris) FROM skader WHERE aftale_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, aftaleId);
    }

}
