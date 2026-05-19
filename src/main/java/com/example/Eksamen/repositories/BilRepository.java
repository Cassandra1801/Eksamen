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


    ///Finder ud af om en bil eksisterer med det vognnummer i biler tabellen (registrering af lejeaftale funktionalitet)
    public boolean eksistererVognnummeret(String vognnummer) {

        //SQL Statement returnerer boolean om eksistensen af vognnummer i biler
        //1 Er for at den ikke går igennem resten af listen da den har fundet bilen (vognnummer er unique (PK))
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
    public boolean erBilenLedig(String vognnummer) {

        //SQL Statement boolean
        //1 Er for at den ikke går igennem resten af listen da den har fundet vognnummer (vognnummer er unique (PK))
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
    public void opdaterStatus(String vognnummer, BilStatus status) {

        //SQL Statement
        String sql = "UPDATE biler SET  status = ? WHERE vognnummer = ?";

        //.name() laver en enum om til en string, og SQL forventer en string, selv om det er af typen enum
        jdbcTemplate.update(sql, status.name(), vognnummer);
    }

    ///Finder alle bilerne der samsvarer parametrerne, og returnerer dem i en liste
    public List<Bil> findFiltreredeBiler(boolean limited, boolean unlimited, String maerke) {

        // 1 = 1, er for at man slipper fra at skule håndtere om det skal være WHERE eller AND,
        // Nu kan man bare bruge AND til alle string tilføjelser.
        // Det fungerer fordi 1 = 1 er altid true, så den vil bare hente
        // alle biler, vis ingen parametrer er tilføjet
        String sql = """
                SELECT * FROM biler
                WHERE 1 = 1
                """;

        //liste med de tilføjede parametrer til at bruge i query
        List<Object> parametrer = new ArrayList<>();

        //Mulig tilføjelse til SQL statement
        if (limited && !unlimited) {
            sql += " AND bil_type = ?";
            parametrer.add("LIMITED");
        }

        //Mulig tilføjelse til SQL statement
        if (!limited && unlimited) {
            sql += " AND bil_type = ?";
            parametrer.add("UNLIMITED");
        }

        //Mulig tilføjelse til SQL statement
        //isBlank() fikser ting som "" eller "  ".
        if (maerke != null && !maerke.isBlank()) {
            sql += " AND maerke = ?";
            parametrer.add(maerke);
        }

        //returnerer liste i forhold til query på den færdige
        //SQL statement til de tilføjede parametrer
        return jdbcTemplate.query(sql, bilRowMapper, parametrer.toArray());

    }

    ///Returnerer en liste med alle "maerker" på bilerne der er i databasen
    public List<String> findAlleMaerker() {

        //Henter maerker og sorterer dem
        String sql = "SELECT DISTINCT maerke FROM biler ORDER BY maerke";

        return jdbcTemplate.queryForList(sql, String.class);
    }



    /* Bruger Single Table Inheritance:
     alle biler i samme tabel, med bil_type-kolonnen som diskriminator.
     RowMapper vælger subklasse ud fra den kolonne */

    /* Bruges af findAlle() og findVedVognnummer() til at bygge Bil-objekter fra ResultSet-rækker
     Læser en række fra biler-tabellen og bygger den korrekte Bil-subklasse
     ud fra bil_type-kolonnen. Sætter derefter de fælles felter fra superklassen */
     /* Implementeret som anonym inner class: RowMapper<Bil> er et interface med én metode, og der er kun brug for én implementering ét sted i koden.
     Klassen erklæres og instansieres derfor direkte med "new RowMapper<Bil> frem for at oprette en separat, navngivet klasse i en egen .java-fil */
    private final RowMapper<Bil> bilRowMapper = new RowMapper<Bil>() {
        @Override
        public Bil mapRow(ResultSet rs, int rowNum) throws SQLException {
            String type = rs.getString("bil_type");
            Bil bil;

            if ("LIMITED".equals(type)) {
                bil = new LimitedBil();
            } else if ("UNLIMITED".equals(type)) {
                UnlimitedBil u = new UnlimitedBil();
                u.setAftalePeriodeIMaaneder(rs.getInt("aftalte_periode_i_maaneder"));
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

    /** Henter alle biler fra databasen
     * Subklasse.typen bestemmes af RowMapper*/
    public List<Bil> findAlle() {
        return jdbcTemplate.query("SELECT * FROM biler", bilRowMapper);
    }

    ///  Tæller alle biler i databasen
    public int antalBiler() {

        String sql = "SELECT COUNT(*) FROM biler";

        Integer antal = jdbcTemplate.queryForObject(sql, Integer.class);

        if (antal == null) {
            return 0;
        }

        return antal;
    }

    ///  Tæller biler med en bestemt status
    public int antalMedStatus(BilStatus status) {

        String sql = "SELECT COUNT(*) FROM biler WHERE status = ?";

        Integer antal = jdbcTemplate.queryForObject(sql, Integer.class, status.name());

        if (antal == null) {
            return 0;
        }

        return antal;
    }

    // Tjekker om en bil er tilbageleveret OG har en udløbet lejeperiode
    public boolean erKlarTilSkaderegistrering(String vognnummer) {

        /** SQL Statement: bilen skal have status TILBAGELEVERET,
        * og dens seneste lejeaftale skal være udløbet (start_dato + antal_maaneder < i dag) */
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


    ///  Henter alle biler der er klar til skaderegistrering
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


    /// Gemmer en bil i databasen som Limited eller Unlimited
    public int gem(Bil bil) {
        // Return type er en int fordi jdbcTemplate.update returnerer
        // en int som er antal rækker i databasen der er påvirket.


        // LIMITED
        if (bil instanceof LimitedBil) {

            //SQL Statement
            String sql = """
            INSERT INTO biler
            (vognnummer, stelnummer, maerke, model,
             udstyrsniveau, staalpris, reg_afgift,
             co2_udledning, farve, status, bil_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            //Opretter en bil i databasen
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
                    "LIMITED"
            );
        }

        // UNLIMITED
        else if (bil instanceof UnlimitedBil u) {

            //SQL Statement
            String sql = """
            INSERT INTO biler
            (vognnummer, stelnummer, maerke, model,
             udstyrsniveau, staalpris, reg_afgift,
             co2_udledning, farve, status,
             bil_type, aftalte_periode_i_maaneder)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

            //Opretter en bil i databasen
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
                    "UNLIMITED",
                    u.getAftalePeriodeIMaaneder()
            );
        }

        else {
            throw new IllegalArgumentException("Ukendt biltype");
        }
    }
}
