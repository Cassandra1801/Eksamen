package com.example.Eksamen.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lejeaftale {

    private int lejeAftaleId;
    private String medarbejderId;
    private int kundeId;
    private String vognnummer;
    private String lokation;
    private LocalDate startDato;
    private int antalMaaneder;
    private BigDecimal prisPrMaaned;
    private int kmGraense;
    
    public Lejeaftale () {}

    public Lejeaftale (int lejeAftaleId, String medarbejderId,  int kundeId, String vognnummer, String lokation, LocalDate startDato, int antalMaaneder, BigDecimal prisPrMaaned, int kmGraense) {
        this.lejeAftaleId = lejeAftaleId;
        this.medarbejderId = medarbejderId;
        this.kundeId = kundeId;
        this.vognnummer = vognnummer;
        this.lokation = lokation;
        this.startDato = startDato;
        this.antalMaaneder = antalMaaneder;
        this.prisPrMaaned = prisPrMaaned;
        this.kmGraense = kmGraense;
    }

    public int getLejeaftaleId() {return lejeAftaleId;}
    public void setLejeaftaleId(int lejeaftaleId) {this.lejeAftaleId = lejeaftaleId;}

    public String getMedarbejderId() {return medarbejderId;}
    public void setMedarbejderId(String medarbejderId) {this.medarbejderId = medarbejderId;}

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public String getVognnummer() {return vognnummer;}
    public void setVognnummer(String vognnummer) {this.vognnummer = vognnummer;}

    public String getLokation() {return lokation;}
    public void setLokation(String lokation) {this.lokation = lokation;}

    public LocalDate getStartDato() {return startDato;}
    public void setStartDato(LocalDate startDato) {this.startDato = startDato;}

    public int getAntalMaaneder() {return antalMaaneder;}
    public void setAntalMaaneder(int antalMaaneder) {this.antalMaaneder = antalMaaneder;}

    public BigDecimal getPrisPrMaaned() {return prisPrMaaned;}
    public void setPrisPrMaaned(BigDecimal pris) {this.prisPrMaaned = pris;}

    public int getKmGraense() {return kmGraense;}
    public void setKmGraense(int kmGraense) {this.kmGraense = kmGraense;}
}
