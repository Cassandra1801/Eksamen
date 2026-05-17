package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Skadesrapport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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

    public boolean kanRegistrereSkade(String vognnummer, int lejeaftaleId, LocalDate dato) {
        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM biler b
                    INNER JOIN lejeaftaler l ON l.vognnummer = b.vognnummer
                    WHERE b.vognnummer = ?
                      AND l.lejeaftale_Id = ?
                      AND b.status = 'TILBAGELEVERET'
                      AND DATE_ADD(l.startDato, INTERVAL l.antalMaaneder MONTH) <= ?
                )
                """;

        Boolean kanRegistreres = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                vognnummer,
                lejeaftaleId,
                dato
        );

        return Boolean.TRUE.equals(kanRegistreres);
    }

    public double totalPris(String vognnummer) {
        String sql = "SELECT SUM(pris) FROM skader WHERE vognnummer = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, vognnummer);
    }

}
