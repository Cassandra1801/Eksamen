package com.example.Eksamen.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lejeaftale {

    private int lejeaftaleId;
    private String medarbejderId;
    private int kundeId;
    private int vognnummer;
    private String lokation;
    private LocalDate startdato;
    private LocalDate slutdato;
    private BigDecimal prisPrMaaned;
    private int kmGraense;
    
    public Lejeaftale () {}

    public Lejeaftale (int kundeId, int vognnummer, String lokation, LocalDate startdato, LocalDate slutdato, BigDecimal prisPrMaaned, int kmGraense) {
        this.kundeId = kundeId;
        this.vognnummer = vognnummer;
        this.lokation = lokation;
        this.startdato = startdato;
        this.slutdato = slutdato;
        this.prisPrMaaned = prisPrMaaned;
        this.kmGraense = kmGraense;
    }

    public int getLejeaftaleId() {
        return lejeaftaleId;
    }

    public void setLejeaftaleId(int lejeaftaleId) {
        this.lejeaftaleId = lejeaftaleId;
    }

    public String getMedarbejderId() {
        return medarbejderId;
    }

    public void setMedarbejderId(String medarbejderId) {
        this.medarbejderId = medarbejderId;
    }

    public int getKundeId() {
        return kundeId;
    }

    public void setKundeId(int kundeId) {
        this.kundeId = kundeId;
    }

    public int getVognnummer() {
        return vognnummer;
    }

    public void setVognnummer(int vognnummer) {
        this.vognnummer = vognnummer;
    }

    public String getLokation() {
        return lokation;
    }

    public void setLokation(String lokation) {
        this.lokation = lokation;
    }

    public LocalDate getStartdato() {
        return startdato;
    }

    public void setStartdato(LocalDate startdato) {
        this.startdato = startdato;
    }

    public LocalDate getSlutdato() {
        return slutdato;
    }

    public void setSlutdato(LocalDate slutdato) {
        this.slutdato = slutdato;
    }

    public BigDecimal getPrisPrMaaned() {
        return prisPrMaaned;
    }

    public void setPrisPrMaaned(BigDecimal prisPrMaaned) {
        this.prisPrMaaned = prisPrMaaned;
    }

    public int getKmGraense() {
        return kmGraense;
    }

    public void setKmGraense(int kmGraense) {
        this.kmGraense = kmGraense;
    }




    
}
