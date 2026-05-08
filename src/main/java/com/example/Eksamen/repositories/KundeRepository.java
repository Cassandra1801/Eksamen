package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Kunde;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class KundeRepository {

    ///Vi bruger JdbcTemplate, fordi det er en wrapper der holder koden renere
    /// og med mindre fejl risiko, i forhold til Jdbc, hvor man skal forbinde
    /// og lukke databasen og statements hver gang. JdbcTemplate gør det for os.
    private final JdbcTemplate jdbcTemplate;

    public KundeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    ///Finder en bruger fra databasen via email (registrering af lejeaftale funktionalitet)
    public Optional<Kunde> findMedEmail(String email) {

        //SQL Statement
        String sql = "SELECT * FROM kunder WHERE email = ?";

        //Returnerer resultaterne fra vores query via vores statement der matcher emailen der kommer for enden *
        //Et ResultSet er et objekt som indeholder det data en SQL query henter (kun det specifikke data)
        return jdbcTemplate.query(sql, rs -> {

            //rs.next() giver os en boolean som er true, vis den næste linje er der og false hvis der ikke er flere
            if (rs.next()) {
                Kunde kunde = new Kunde();
                kunde.setKundeId(rs.getInt("kunde_id"));
                kunde.setNavn(rs.getString("navn"));
                kunde.setMobil(rs.getString("mobil"));
                kunde.setEmail(rs.getString("email"));
                return Optional.of(kunde); //returner den kunde der blev fundet
            }
            //optional returnerer et "empty optional" vis det ønskede ikke blev fundet for at undgå at returnere null
            return Optional.empty();

            // * denne mail
        }, email);
    }


    /// Laver en kunde i databasen og returnerer dets ID (registrering af lejeaftale funktionalitet)
    public int opretKunde(Kunde kunde) {

        //SQL Statement (hvert ? er en placeholder som bliver fyldt)
        String sql = """
                INSERT INTO kunder (navn, mobil, email)
                VALUES (?,?,?)
        """;

        //Tilføjer til tabellen "kunder" en ny kunde via kunde objektet som er parameteret på funktionen
        jdbcTemplate.update(
                sql,
                kunde.getNavn(), //Værdien af det første ?
                kunde.getMobil(), //Værdien af det næste ?
                kunde.getEmail() //Værdien af det tredje ?
        );

        //Efter at kunden er oprettet vil den med det samme returnere det tildelte ID
        //LAST_INSERT_ID() returnerer det seneste tildelte id,
        // som i dette tilfælde er den kunde vi lige lavede
        return jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class
        );
    }

}
