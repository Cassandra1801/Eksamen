package com.example.Eksamen.models;

import java.math.BigDecimal;

public class BilForm {

    /// Denne klasse er lavet for at understøtte oprettelsen af nye biler
    /// der understøtter begge biltyper.

    // Fælles felter fra de to Bil typer
    private String vognnummer;
    private String stelnummer;
    private String maerke;
    private String model;
    private String udstyrsniveau;
    private BigDecimal staalpris;
    private int regAfgift;
    private int co2Udledning;
    private String farve;
    private BilStatus status;

    // Bruges til at vælge subtype
    // "LIMITED" eller "UNLIMITED"
    private String abonnementsType;

    // Kun relevant for UnlimitedBil
    private Integer aftaltePeriodeIMaaneder;

    public BilForm() {
    }

    public String getVognnummer() {
        return vognnummer;
    }

    public void setVognnummer(String vognnummer) {
        this.vognnummer = vognnummer;
    }

    public String getStelnummer() {
        return stelnummer;
    }

    public void setStelnummer(String stelnummer) {
        this.stelnummer = stelnummer;
    }

    public String getMaerke() {
        return maerke;
    }

    public void setMaerke(String maerke) {
        this.maerke = maerke;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUdstyrsniveau() {
        return udstyrsniveau;
    }

    public void setUdstyrsniveau(String udstyrsniveau) {
        this.udstyrsniveau = udstyrsniveau;
    }

    public BigDecimal getStaalpris() {
        return staalpris;
    }

    public void setStaalpris(BigDecimal staalpris) {
        this.staalpris = staalpris;
    }

    public int getRegAfgift() {
        return regAfgift;
    }

    public void setRegAfgift(int regAfgift) {
        this.regAfgift = regAfgift;
    }

    public int getCo2Udledning() {
        return co2Udledning;
    }

    public void setCo2Udledning(int co2Udledning) {
        this.co2Udledning = co2Udledning;
    }

    public String getFarve() {
        return farve;
    }

    public void setFarve(String farve) {
        this.farve = farve;
    }

    public BilStatus getStatus() {
        return status;
    }

    public void setStatus(BilStatus status) {
        this.status = status;
    }

    public String getAbonnementsType() {
        return abonnementsType;
    }

    public void setAbonnementsType(String abonnementsType) {
        this.abonnementsType = abonnementsType;
    }

    public Integer getAftaltePeriodeIMaaneder() {
        return aftaltePeriodeIMaaneder;
    }

    public void setAftaltePeriodeIMaaneder(Integer aftaltePeriodeIMaaneder) {
        this.aftaltePeriodeIMaaneder = aftaltePeriodeIMaaneder;
    }
}