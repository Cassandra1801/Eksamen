package com.example.Eksamen.repositories;

import com.example.Eksamen.models.Bil;
import com.example.Eksamen.models.Lejeaftale;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
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


    ///Opretter en lejeaftale inde på databasen
    public void opretLejeaftale(Lejeaftale lejeaftale) {

        //SQL Statement
        String sql = """
                INSERT INTO lejeaftaler
                (medarbejder_Id, kunde_Id, vognnummer, lokation, start_dato, antal_maaneder, pris_pr_maaned, km_graense)
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
        String sql = "SELECT SUM(pris_pr_maaned) FROM lejeaftaler WHERE DATE_ADD(start_dato, INTERVAL antal_maaneder MONTH) >= CURDATE()";
        Double resultat = jdbcTemplate.queryForObject(sql, Double.class);

        /* SUM() returnerer NULL hvis ingen lejeaftaler er aktive.
            Returnerer 0.0 i stedet, så NULL ikke unboxes til double (NullPointerException) */
        if (resultat == null) {
            return 0.0;
        }
        return resultat;
    }

    public final RowMapper<Lejeaftale> lejeaftaleRowMapper = new RowMapper<Lejeaftale>() {
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

        // 1 = 1, er for at man slipper fra at skule håndtere om det skal være WHERE eller AND,
        // Nu kan man bare bruge AND til alle string tilføjelser.
        // Det fungerer fordi 1 = 1 er altid true, så den vil bare hente
        // alle biler, vis ingen parametrer er tilføjet
        // Bruger inner join for at få informationerne fra kunde da man søger på lejeaftale
        String sql = """
                SELECT l.*
                FROM lejeaftaler l 
                INNER JOIN kunder k ON l.kunde_id = k.kunde_id
                WHERE 1 = 1
                """;

        List<Object> parametrer = new ArrayList<>();

        //Mulig tilføjelse til SQL statement
        //isBlank() fikser ting som "" eller "  ".
        if (sogning != null && !sogning.isBlank()) {
            sql += """
                   AND (
                   k.navn LIKE ?
                   OR k.email LIKE ?
                   OR l.vognnummer LIKE ?
                   OR k.mobil LIKE ?
                   )
                   """;

            // % er for at man kann kun skrive efternavnet og hele navnet kommer frem.
            // Der kan være hvad som helst på hver side af sogningen.
            String search = "%" + sogning + "%";

            parametrer.add(search);
            parametrer.add(search);
            parametrer.add(search);
            parametrer.add(search);
        }

        //returnerer liste i forhold til query på den færdige
        //SQL statement til de tilføjede parametrer
        return jdbcTemplate.query(sql, lejeaftaleRowMapper, parametrer.toArray());

    }



}
