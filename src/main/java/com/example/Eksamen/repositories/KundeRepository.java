package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Kunde;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class KundeRepository {

    // JdbcTemplate håndterer databaseforbindelser og gør repository-koden enklere.
    private final JdbcTemplate jdbcTemplate;

    public KundeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Finder en kunde via email
    public Optional<Kunde> findMedEmail(String email) {

        String sql = "SELECT * FROM kunder WHERE email = ?";

        // Returnerer resultaterne fra vores query via vores statement der matcher emailen der kommer for enden
        // Et ResultSet er et objekt som indeholder det data en SQL query henter (kun det specifikke data)
        return jdbcTemplate.query(sql, rs -> {

            // Hvis query'en finder en række, bygges et Kunde-objekt
            if (rs.next()) {
                Kunde kunde = new Kunde();
                kunde.setKundeId(rs.getInt("kunde_id"));
                kunde.setNavn(rs.getString("navn"));
                kunde.setMobil(rs.getString("mobil"));
                kunde.setEmail(rs.getString("email"));
                return Optional.of(kunde);
            }
            // Optional.empty() bruges i stedet for null, når kunden ikke findes
            return Optional.empty();

        }, email);
    }


    // Opretter en kunde og returnerer det nye kunde-id
    public int opretKunde(Kunde kunde) {

        // Hvert ? er en placeholder som bliver udfyldt
        String sql = """
                INSERT INTO kunder (navn, mobil, email)
                VALUES (?,?,?)
        """;

        //Tilføjer til tabellen "kunder" en ny kunde via kunde objektet som er parameteret på funktionen
        jdbcTemplate.update(
                sql,
                kunde.getNavn(),
                kunde.getMobil(),
                kunde.getEmail()
        );

        // LAST_INSERT_ID() returnerer id'et på kunden, der lige er oprettet
        return jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class
        );
    }
}
