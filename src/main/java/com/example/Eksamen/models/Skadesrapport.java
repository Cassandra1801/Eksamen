package com.example.Eksamen.models;

import java.time.LocalDate;

public class Skadesrapport {

    private int skadeId;
    private String vognnummer;
    private int lejeaftaleId;
    private int medarbejderId;
    private LocalDate dato;
    private String beskrivelse;
    private double pris;



    public Skadesrapport(){}

    public int getSkadeId() {return skadeId;}
    public void setSkadeId(int skadeid) {this.skadeId = skadeid;}

    public String getVognnummer() {return vognnummer;}
    public void setVognnummer(String vognnummer) {this.vognnummer = vognnummer;}

    public int getLejeaftaleId() {return lejeaftaleId;}
    public void setLejeaftaleId(int aftaleid) {this.lejeaftaleId = aftaleid;}

    public int getMedarbejderId() {return medarbejderId;}
    public void setMedarbejderId(int medarbejderId) {this.medarbejderId = medarbejderId;}

    public LocalDate getDato() { return dato; }
    public void setDato(LocalDate dato) { this.dato = dato; }

    public String getBeskrivelse() {return beskrivelse;}
    public void setBeskrivelse(String beskrivelse) {this.beskrivelse = beskrivelse;}

    public double getPris() {return pris;}

    public void setPris(double pris) {
        if (pris < 0) {
            throw new IllegalArgumentException("Pris må ikke være negativ");
        }
        this.pris = pris;
    }
}