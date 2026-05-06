package com.example.Eksamen.models;

import java.time.LocalDate;

public class Skadesrapport {

    private int skadeId;
    private int bilId;
    private int lejeaftaleId;
    private int medarbejderId;
    private LocalDate dato;
    private String beskrivelse;
    private double pris;
    private int totalPris;



    public Skadesrapport(){}

    public int getSkadeId() {return skadeId;}
    public void setSkadeId(int skadeid) {this.skadeId = skadeid;}

    public int getBilId() {return bilId;}
    public void setBilId(int bilId) {this.bilId = bilId;}

    public int getAftaleid() {return lejeaftaleId;}
    public void setAftaleId(int aftaleid) {this.lejeaftaleId = aftaleid;}

    public int getMedarbejderId() {return medarbejderId;}
    public void setMedarbejderId(int medarbejderId) {this.medarbejderId = medarbejderId;}

    public LocalDate getDato() { return dato; }
    public void setDato(LocalDate dato) { this.dato = dato; }

    public String getBeskrivelse() {return beskrivelse;}
    public void setBeskrivelse(String beskrivelse) {this.beskrivelse = beskrivelse;}

    public double getPris() {return pris;}
    public void setPris(int pris) {this.pris = pris;}

    public int getTotalPris() {return totalPris;}
    public void setTotalPris(int totalPris) {this.totalPris = totalPris;}


}