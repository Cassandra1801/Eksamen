package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.models.LimitedBil;
import com.example.Eksamen.models.UnlimitedBil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    /// RowMapper der vælger rigtige subklasser ud fra bil_type-kolonnen
    ///  Bruges af findAlle() og findVedVognnummer() til at bygge Bil-objekter
    ///  fra ResultSet-rækker
    private final RowMapper<Bil> bilRowMapper = (rs, rowNum) -> {
        String type = rs.getString("bil_type");
        Bil bil;

        if("LIMITED".equals(type)) {
            bil = new LimitedBil();
        } else if ("UNLIMITED".equals(type)) {
            UnlimitedBil u = new UnlimitedBil();
            u.setAftaltePeriodeIMaaneder(rs.getInt("aftalte_periode_i_maaneder"));
            bil = u;
        } else {
            throw new IllegalStateException("Ukendt bil_type: " + type);
        }

        // Fælles felter fra Bil-superklassen
        bil.setVognnummer(rs.getString("vognnummer"));
        bil.setStelnummer(rs.getString("stelnummer"));
        bil.setMaerke(rs.getString("maerke"));
        bil.setModel(rs.getString("model"));
        bil.setUdstyrsniveau(rs.getString("udstyrsniveau"));
        bil.setStaalpris(rs.getInt("staalpris"));
        bil.setRegAfgift(rs.getInt("reg_afgift"));
        bil.setCo2Udledning(rs.getInt("co2_udledning"));
        bil.setFarve(rs.getString("farve"));
        bil.setStatus(BilStatus.valueOf(rs.getString("status")));

        return bil;
    } ;

    // Henter alle biler fra databasen
    public List<Bil> findAlle() {
        return jdbcTemplate.query("SELECT * FROM biler", bilRowMapper);
    }

    // Henter en specifik bil ud fra vognnummer
    public Bil findVedVognnummer(int vognnummer) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM biler WHERE vognnummer = ?",
                bilRowMapper, vognnummer);
    }

    ///  Gemmer en bil i databasen. Vælger SQL ud fra subklasse-typen,
    ///  så aftalte_perioder_i_maaneder kun sættes for UnlimitedBil
    public int gem(Bil bil) {
        if (bil instanceof LimitedBil) {
            return jdbcTemplate.update("""
                    INSERT INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau,
                                 staalpris, registreringsafgift, co2_udledning, farve, bil_type)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'LIMITED')
                    """,
                    bil.getVognnummer(), bil.getStelnummer(), bil.getMaerke(),
                    bil.getModel(), bil.getUdstyrsniveau(), bil.getStaalpris(),
                    bil.getRegAfgift(), bil.getCo2Udledning(), bil.getFarve(),
                    bil.getStatus().name());
        } else if (bil instanceof UnlimitedBil u) {
            return jdbcTemplate.update("""
                    INSERT INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau,
                                 staalpris, registreringsafgift, co2_udledning, farve,
                                 bil_type, aftalte_periode_i_maaneder)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'UNLIMITED', ?)
                    """,
                    u.getVognnummer(), u.getStelnummer(), u.getMaerke(),
                    u.getModel(), u.getUdstyrsniveau(), u.getStaalpris(),
                    u.getRegAfgift(), u.getCo2Udledning(), u.getFarve(),
                    u.getStatus().name(),
                    u.getAftaltePeriodeIMaaneder());
        }
        throw new IllegalArgumentException("Ukendt biltype");
    }
}
