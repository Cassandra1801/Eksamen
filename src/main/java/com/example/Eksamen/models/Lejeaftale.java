package com.example.Eksamen.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lejeaftale {

    private int lejeAftaleId;
    private int medarbejderId;
    private int kundeId;
    private int vognnummer;
    private String lokation;
    private LocalDate startDato;
    private LocalDate slutDato;
    private BigDecimal prisPrMaaned;
    private int kmGraense;
    
    public Lejeaftale () {}

    public Lejeaftale (int lejeAftaleId, int medarbejderId,  int kundeId, int vognnummer, String lokation, LocalDate startDato, LocalDate slutDato, BigDecimal prisPrMaaned, int kmGraense) {
        this.vognnummer = vognnummer;
        this.kundeId = kundeId;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.prisPrMaaned = prisPrMaaned;
        this.kmGraense = kmGraense;
    }

    public int getAftaleId() {return lejeAftaleId;}
    public void setAftaleId(int aftaleId) {this.lejeAftaleId = aftaleId;}

    public int getMedarbejderId() {return medarbejderId;}
    public void setMedarbejderId(int medarbejderId) {this.medarbejderId = medarbejderId;}

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public int getVognnummer() {return vognnummer;}
    public void setVognnummer(int vognnummer) {this.vognnummer = vognnummer;}

    public String getLokation() {return lokation;}
    public void setLokation(String lokation) {this.lokation = lokation;}

    public LocalDate getStartDato() {return startDato;}
    public void setStartDato(LocalDate startDato) {this.startDato = startDato;}

    public LocalDate getSlutDato() {return slutDato;}
    public void setSlutDato(LocalDate slutDato) {this.slutDato = slutDato;}

    public BigDecimal getPris() {return pris;}
    public void setPris(BigDecimal pris) {this.pris = pris;}
}
