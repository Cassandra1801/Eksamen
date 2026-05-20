package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Lejeaftale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LejeaftaleRepository {

    private final JdbcTemplate jdbcTemplate;

    public LejeaftaleRepository (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Opretter en lejeaftale i databasen
    public void opretLejeaftale(Lejeaftale lejeaftale) {

        String sql = """
                INSERT INTO lejeaftaler
                (medarbejder_Id, kunde_Id, vognnummer, lokation, start_dato, antal_maaneder, pris_pr_maaned, km_graense)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                lejeaftale.getMedarbejderId(),
                lejeaftale.getKundeId(),
                lejeaftale.getVognnummer(),
                lejeaftale.getLokation(),
                lejeaftale.getStartDato(),
                lejeaftale.getAntalMaaneder(),
                lejeaftale.getPrisPrMaaned(),
                lejeaftale.getKmGraense()
        );
    }

    public double sammenlagtPrisUdlejede() {
        String sql = "SELECT SUM(pris_pr_maaned) FROM lejeaftaler WHERE DATE_ADD(start_dato, INTERVAL antal_maaneder MONTH) >= CURDATE()";
        Double resultat = jdbcTemplate.queryForObject(sql, Double.class);

        /* SUM() returnerer NULL hvis ingen lejeaftaler er aktive.
           Derfor returneres 0.0 i stedet. */
        if (resultat == null) {
            return 0.0;
        }
        return resultat;
    }

    /// Lejeaftale RowMapper
    private final RowMapper<Lejeaftale> lejeaftaleRowMapper = new RowMapper<Lejeaftale>() {
        /* Den ser ud som en metode og en variabel, fordi at objektet
            i variabelen er lavet inline, som betyder at det er lavet med det samme på variabelen selv */
        @Override
        public Lejeaftale mapRow(ResultSet rs, int rowNum) throws SQLException {

            Lejeaftale lejeaftale = new Lejeaftale();

            lejeaftale.setLejeaftaleId(rs.getInt("lejeaftale_id"));
            lejeaftale.setMedarbejderId(rs.getString("medarbejder_id"));
            lejeaftale.setKundeId(rs.getInt("kunde_id"));
            lejeaftale.setVognnummer(rs.getString("vognnummer"));
            lejeaftale.setLokation(rs.getString("lokation"));
            lejeaftale.setStartDato(rs.getDate("start_dato").toLocalDate());
            lejeaftale.setAntalMaaneder(rs.getInt("antal_maaneder"));
            lejeaftale.setPrisPrMaaned(rs.getBigDecimal("pris_pr_maaned"));
            lejeaftale.setKmGraense(rs.getInt("km_graense"));

            return lejeaftale;
        }
    };

    public List<Lejeaftale> findAlle() {
        return jdbcTemplate.query("SELECT * FROM lejeaftaler", lejeaftaleRowMapper);
    }


    // Finder lejeaftaler hvor søgningen matcher kunde navn, email, mobil eller vognnummer
    public List<Lejeaftale> findFiltreredeLejeaftaler(String sogning) {

        // WHERE 1 = 1 gør det muligt at tilføje alle filtre med AND.
        // Der bruges join, så søgningen også kan matche kundedata.
        String sql = """
                SELECT l.*
                FROM lejeaftaler l 
                INNER JOIN kunder k ON l.kunde_id = k.kunde_id
                WHERE 1 = 1
                """;

        List<Object> parametrer = new ArrayList<>();

        // isBlank() håndterer både "" og "  "
        if (sogning != null && !sogning.isBlank()) {
            sql += """
                   AND (
                   k.navn LIKE ?
                   OR k.email LIKE ?
                   OR l.vognnummer LIKE ?
                   OR k.mobil LIKE ?
                   )
                   """;

            // % gør søgningen fleksibel, så teksten kan stå hvor som helst i feltet
            String search = "%" + sogning + "%";

            parametrer.add(search);
            parametrer.add(search);
            parametrer.add(search);
            parametrer.add(search);
        }

        return jdbcTemplate.query(sql, lejeaftaleRowMapper, parametrer.toArray());

    }



}
