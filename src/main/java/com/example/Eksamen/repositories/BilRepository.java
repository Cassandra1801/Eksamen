package com.example.Eksamen.repositories;

import com.example.Eksamen.models.BilStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BilRepository {

    private final JdbcTemplate jdbcTemplate;

    public BilRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    ///Finder ud af om en bil eksisterer med det vognnummer i biler tabellen (registrering af lejeaftale funktionalitet)
    public boolean eksistererVognnummeret(int vognnummer) {

        //SQL Statement returnerer boolean om eksistensen af vognnummer i biler
        String sql = """
                SELECT EXISTS (
                    SELECT 1 FROM biler WHERE vognnummer = ?
                );
                """;

        //Boolean variabel via statement
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, vognnummer);

        //Returnerer false vis exists er false eller null. Forhindrer NullPointerException
        return Boolean.TRUE.equals(exists);
    }


    ///Finder ud af om en bil er ledig inde på biler tabellen via vognnummer (registrering af lejeaftale funktionalitet)
    public boolean erBilenLedig(int vognnummer) {

        //SQL Statement boolean
        String sql = """
                SELECT EXISTS (
                    SELECT 1 FROM biler WHERE vognnummer = ? AND status = 'LEDIG'
                );
                """;

        //Boolean variabel via statement
        Boolean ledig = jdbcTemplate.queryForObject(sql, Boolean.class, vognnummer);

        //Returnerer false vis ledig er false eller null. Forhindrer NullPointerException
        return Boolean.TRUE.equals(ledig);
    }


    ///Ændrer ledig status på bil (registrering af lejeaftale funktionalitet)
    public void opdaterStatus(int vognnummer, BilStatus status) {

        //SQL Statement
        String sql = "UPDATE biler SET  status = ? WHERE vognnummer = ?";

        //.name() laver en enum om til en string, og SQL forventer en string, selv om det er af typen enum
        jdbcTemplate.update(sql, status.name(), vognnummer);
    }

}
