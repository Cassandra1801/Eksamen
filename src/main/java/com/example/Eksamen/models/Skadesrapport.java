package com.example.Eksamen.models;

import java.time.LocalDate;

public class Skadesrapport {

    private int skadeid;
    private int aftaleid;
    private String beskrivelse;
    private double pris;
    private int totalPris;
    private LocalDate dato;


    public Skadesrapport(){}

    public int getSkadeid() {return skadeid;}
    public void setSkadeid(int skadeid) {this.skadeid = skadeid;}

    public int getAftaleid() {return aftaleid;}
    public void setAftaleid(int aftaleid) {this.aftaleid = aftaleid;}

    public String getBeskrivelse() {return beskrivelse;}
    public void setBeskrivelse(String beskrivelse) {this.beskrivelse = beskrivelse;}

    public double getPris() {return pris;}
    public void setPris(int pris) {this.pris = pris;}

    public int getTotalPris() {return totalPris;}
    public void setTotalPris(int totalPris) {this.totalPris = totalPris;}

    public LocalDate getDato() { return dato; }
    public void setDato(LocalDate dato) { this.dato = dato; }

}