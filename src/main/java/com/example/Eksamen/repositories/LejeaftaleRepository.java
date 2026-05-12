package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Lejeaftale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LejeaftaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public LejeaftaleRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    ///Opretter en lejeaftale inde på databasen
    public void opretLejeaftale(Lejeaftale lejeaftale) {

        //SQL Statement
        String sql = """
                INSERT INTO lejeaftaler
                (medarbejder_Id, kunde_Id, vognnummer, lokation, startDato, antalMaaneder, pris_pr_maaned, km_graense)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                lejeaftale.getMedarbejderId(), //Værdien af det første ?
                lejeaftale.getKundeId(), //Værdien af det andet ?
                lejeaftale.getVognnummer(), //Værdien af det tredje ?
                lejeaftale.getLokation(), //Værdien af det fjerde ?
                lejeaftale.getStartDato(), //Værdien af det femte ?
                lejeaftale.getAntalMaaneder(), //Værdien af det sjette ?
                lejeaftale.getPrisPrMaaned(), //Værdien af det syvende ?
                lejeaftale.getKmGraense() //Værdien af det åttende ?
        );
    }

    public double sammenlagtPrisUdlejede() {
        String sql = "SELECT SUM(pris_pr_maaned) FROM lejeaftaler WHERE DATE_ADD(startDato, INTERVAL antalMaaneder MONTH) >= CURDATE()";
        return jdbcTemplate.queryForObject(sql, Double.class);
    }
}
