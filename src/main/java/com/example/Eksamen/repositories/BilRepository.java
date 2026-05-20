package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.BilStatus;
import com.example.Eksamen.models.LimitedBil;
import com.example.Eksamen.models.UnlimitedBil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BilRepository {

    private final JdbcTemplate jdbcTemplate;

    public BilRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Tjekker om der findes en bil med det angivne vognnummer
    public boolean eksistererVognnummeret(String vognnummer) {

        // SQL statement returnerer boolean om eksistensen af vognnummer i biler
        String sql = """
                SELECT EXISTS (
                    SELECT 1 FROM biler WHERE vognnummer = ?
                );
                """;

        // Boolean variabel via statement
        Boolean exists = jdbcTemplate.queryForObject(sql, Boolean.class, vognnummer);

        // Returnerer false hvis databasen returnerer false eller null
        return Boolean.TRUE.equals(exists);
    }


    // Tjekker om bilen er ledig
    public boolean erBilenLedig(String vognnummer) {

        String sql = """
                SELECT EXISTS (
                    SELECT 1 FROM biler WHERE vognnummer = ? AND status = 'LEDIG'
                );
                """;

        // Boolean variabel via statement
        Boolean ledig = jdbcTemplate.queryForObject(sql, Boolean.class, vognnummer);

        // Returnerer false hvis databasen returnerer false eller null
        return Boolean.TRUE.equals(ledig);
    }


    // Opdaterer bilens status
    public void opdaterStatus(String vognnummer, BilStatus status) {

        String sql = "UPDATE biler SET  status = ? WHERE vognnummer = ?";

        // .name() gemmer enum-værdien som tekst i databasen
        jdbcTemplate.update(sql, status.name(), vognnummer);
    }

    // Finder biler ud fra valgte filtre
    public List<Bil> findFiltreredeBiler(boolean limited, boolean unlimited, String maerke) {

        // WHERE 1 = 1 gør det muligt at tilføje alle filtre med AND.
        // Hvis der ikke vælges filtre, hentes alle biler.
        String sql = """
                SELECT * FROM biler
                WHERE 1 = 1
                """;

        // Parametre til query'en
        List<Object> parametrer = new ArrayList<>();

        // Mulig tilføjelse til SQL statement
        if (limited && !unlimited) {
            sql += " AND bil_type = ?";
            parametrer.add("LIMITED");
        }

        if (!limited && unlimited) {
            sql += " AND bil_type = ?";
            parametrer.add("UNLIMITED");
        }

        // isBlank() håndterer både "" og "  "
        if (maerke != null && !maerke.isBlank()) {
            sql += " AND maerke = ?";
            parametrer.add(maerke);
        }

        // Returnerer liste ift. query på den færdige SQL statement til de tilføjede parametre
        return jdbcTemplate.query(sql, bilRowMapper, parametrer.toArray());

    }

    // Returnerer en sorteret liste med alle bilmærker i databasen
    public List<String> findAlleMaerker() {

        // Henter mærker og sorterer dem
        String sql = "SELECT DISTINCT maerke FROM biler ORDER BY maerke";

        return jdbcTemplate.queryForList(sql, String.class);
    }



    /* Bruger Single Table Inheritance:
     alle biler i samme tabel, med bil_type-kolonnen som diskriminator.
     RowMapper vælger subklasse ud fra den kolonne */

    /* Bruges af repository-metoder til at bygge Bil-objekter fra ResultSet-rækker.
       RowMapperen vælger den rigtige subklasse ud fra bil_type-kolonnen
       og sætter derefter de fælles felter fra Bil-superklassen. */
    private final RowMapper<Bil> bilRowMapper = new RowMapper<Bil>() {
        @Override
        public Bil mapRow(ResultSet rs, int rowNum) throws SQLException {
            String type = rs.getString("bil_type");
            Bil bil;

            if ("LIMITED".equals(type)) {
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
            bil.setStaalpris(rs.getBigDecimal("staalpris"));
            bil.setRegAfgift(rs.getInt("reg_afgift"));
            bil.setCo2Udledning(rs.getInt("co2_udledning"));
            bil.setFarve(rs.getString("farve"));
            bil.setStatus(BilStatus.valueOf(rs.getString("status")));

            return bil;
        }
    };

    public Bil findVedVognnummer(String vognnummer) {
        String sql = "SELECT * FROM biler WHERE vognnummer = ?";
        return jdbcTemplate.queryForObject(sql, bilRowMapper, vognnummer);
    }

    // Tæller alle biler i databasen
    public int antalBiler() {
        String sql = "SELECT COUNT(*) FROM biler";
        Integer antal = jdbcTemplate.queryForObject(sql, Integer.class);
        return antal == null ? 0 : antal;
    }

    // Tæller biler med en bestemt status
    public int antalMedStatus(BilStatus status) {
        String sql = "SELECT COUNT(*) FROM biler WHERE status = ?";
        Integer antal = jdbcTemplate.queryForObject(sql, Integer.class, status.name());
        return antal == null ? 0 : antal;
    }

    // Tjekker om en bil er tilbageleveret og har en udløbet lejeperiode
    public boolean erKlarTilSkaderegistrering(String vognnummer) {

        /* Bilen skal være tilbageleveret, og dens seneste lejeaftale
           skal være udløbet (start_dato + antal_maaneder < i dag). */
        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM biler b
                    JOIN lejeaftaler lej ON b.vognnummer = lej.vognnummer
                    WHERE b.vognnummer = ?
                      AND b.status = 'TILBAGELEVERET'
                        AND DATE_ADD(lej.start_dato, INTERVAL lej.antal_maaneder MONTH) < CURDATE()
                );
                """;
        Boolean klar = jdbcTemplate.queryForObject(sql, Boolean.class, vognnummer);
        return Boolean.TRUE.equals(klar);
    }


    // Henter alle biler der er klar til skaderegistrering
    public List<Bil> findKlarTilSkaderegistrering() {
        String sql = """
            SELECT DISTINCT b.*
            FROM biler b
            JOIN lejeaftaler lej ON b.vognnummer = lej.vognnummer
            WHERE b.status = 'TILBAGELEVERET'
              AND DATE_ADD(lej.start_dato, INTERVAL lej.antal_maaneder MONTH) < CURDATE()
            """;
        return jdbcTemplate.query(sql, bilRowMapper);
    }

    public int gem(Bil bil) {

        // LIMITED
        if (bil instanceof LimitedBil) {

            String sql = """
            INSERT INTO biler
            (vognnummer, stelnummer, maerke, model,
             udstyrsniveau, staalpris, reg_afgift,
             co2_udledning, farve, status, bil_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            return jdbcTemplate.update(
                    sql,
                    bil.getVognnummer(),
                    bil.getStelnummer(),
                    bil.getMaerke(),
                    bil.getModel(),
                    bil.getUdstyrsniveau(),
                    bil.getStaalpris(),
                    bil.getRegAfgift(),
                    bil.getCo2Udledning(),
                    bil.getFarve(),
                    bil.getStatus().name(),
                    bil.getAbonnementsType().toUpperCase()
            );
        }

        // UNLIMITED
        else if (bil instanceof UnlimitedBil u) {

            String sql = """
            INSERT INTO biler
            (vognnummer, stelnummer, maerke, model,
             udstyrsniveau, staalpris, reg_afgift,
             co2_udledning, farve, status,
             bil_type, aftalte_periode_i_maaneder)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            return jdbcTemplate.update(
                    sql,
                    u.getVognnummer(),
                    u.getStelnummer(),
                    u.getMaerke(),
                    u.getModel(),
                    u.getUdstyrsniveau(),
                    u.getStaalpris(),
                    u.getRegAfgift(),
                    u.getCo2Udledning(),
                    u.getFarve(),
                    u.getStatus().name(),
                    u.getAbonnementsType().toUpperCase(),
                    u.getAftaltePeriodeIMaaneder()
            );
        }

        else {
            throw new IllegalArgumentException("Ukendt biltype");
        }
    }
}
