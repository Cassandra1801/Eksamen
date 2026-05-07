package com.example.Eksamen.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lejeaftale {

    private int aftaleId;
    private int vognnummer;
    private int kundeId;
    private LocalDate startDato;
    private LocalDate slutDato;
    private BigDecimal pris;
    
    public Lejeaftale () {}

    public Lejeaftale (int vognnummer, int kundeId, LocalDate startDato, LocalDate slutDato, BigDecimal pris) {
        this.vognnummer = vognnummer;
        this.kundeId = kundeId;
        this.startDato = startDato;
        this.slutDato = slutDato;
        this.pris = pris;
    }

    public int getAftaleId() {return aftaleId;}
    public void setAftaleId(int aftaleId) {this.aftaleId = aftaleId;}

    public int getVognnummer() {return vognnummer;}
    public void setVognnummer(int vognnummer) {this.vognnummer = vognnummer;}

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public LocalDate getStartDato() {return startDato;}
    public void setStartDato(LocalDate startDato) {this.startDato = startDato;}

    public LocalDate getSlutDato() {return slutDato;}
    public void setSlutDato(LocalDate slutDato) {this.slutDato = slutDato;}

    public BigDecimal getPris() {return pris;}
    public void setPris(BigDecimal pris) {this.pris = pris;}
}
