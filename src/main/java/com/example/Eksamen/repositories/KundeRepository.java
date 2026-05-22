package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Kunde;
import com.example.Eksamen.models.Lejeaftale;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class KundeRepository {

    // JdbcTemplate håndterer databaseforbindelser og gør repository-koden enklere.
    private final JdbcTemplate jdbcTemplate;

    public KundeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public final RowMapper<Kunde> kundeRowMapper = new RowMapper<Kunde>() {
        @Override
        public Kunde mapRow(ResultSet rs, int rowNum) throws SQLException {

            Kunde kunde = new Kunde();

            kunde.setKundeId(rs.getInt("kunde_id"));
            kunde.setNavn(rs.getString("navn"));
            kunde.setEmail(rs.getString("email"));
            kunde.setMobil(rs.getString("mobil"));

            return kunde;
        }
    };

    // Finder en kunde via email
    public Optional<Kunde> findMedEmail(String email) {

        String sql = """
            SELECT *
            FROM kunder
            WHERE email = ?
            """;

        try {

            // queryForObject forventer præcis ét resultat
            // kundeRowMapper mapper SQL-resultatet til et Kunde-objekt
            Kunde kunde = jdbcTemplate.queryForObject(
                    sql,
                    kundeRowMapper,
                    email
            );

            // Returnerer kunden wrapped i en Optional hvis den findes
            return Optional.of(kunde);

        } catch (EmptyResultDataAccessException e) {

            // Optional.empty() bruges i stedet for null, når kunden ikke findes
            return Optional.empty();
        }
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


    /// Metode som returnerer en liste af kunder ud fra en liste af lejeaftaler og holder en tilsvarende sortering
    public List<Kunde> findSamsvarendeKunde(List<Lejeaftale> aftaleListe) {

        List<Kunde> kundeListe = new ArrayList<>();

        for (Lejeaftale l : aftaleListe) {

            String sql = """
                    SELECT * FROM kunder
                    WHERE kunde_id = ?
                    """;

            Kunde kunde = jdbcTemplate.queryForObject(sql, kundeRowMapper, l.getKundeId());

            kundeListe.add(kunde);
        }

        return kundeListe;
    }
}
