package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Skadesrapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Fortæller Spring Boot at dette er en Repository klasse
// Spring Boot opretter automatisk et objekt af klassen
@Repository
public class SkadesrapportRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Beder Spring Boot om at give os en JdbcTemplate
    // vi skal ikke selv oprette den med "new"

        public void tilfoejSkadeTilRapport (Skadesrapport skade) {
            // Metoden tager imod et Skadesrapport objekt

            String sql = "INSERT INTO skader (vognnummer, lejeaftale_Id, medarbejder_Id, dato, beskrivelse, pris) VALUES (?,?,?,?,?,?)";
            // SQL sætning der indsætter en ny række i databasen
            // ? er pladsholdere som udfyldes nedenfor

            jdbcTemplate.update(sql,
                    skade.getVognnummer(),
                    skade.getLejeaftaleId(),
                    skade.getMedarbejderId(),
                    skade.getDato(),
                    skade.getBeskrivelse(),
                    skade.getPris()
            );
        }

    public double totalPris(int lejeaftaleId) {
        String sql = "SELECT SUM(pris) FROM skader WHERE lejeaftale_id = ?";
        Double resultat = jdbcTemplate.queryForObject(sql, Double.class, lejeaftaleId);

        /* SUM() returnerer NULL hvis lejeaftale ingen skader har.
            Returnerer 0.0 i stedet, så NULL ikke unboxes til double (NullPointerException) */
        if (resultat == null) {
            return 0.0;
        }
        return resultat;
    }

}
