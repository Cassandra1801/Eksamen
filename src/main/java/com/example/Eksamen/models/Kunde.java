package com.example.Eksamen.models;

public class Kunde {

    private int kundeId;
    private String navn;
    private String email;
    private String mobil;


    public Kunde() {}

    public Kunde(String navn, String telefon, String email) {
        this.navn = navn;
        this.mobil = telefon;
        this.email = email;
    }

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public String getNavn() {return navn;}
    public void setNavn(String navn) {this.navn = navn;}

    public String getMobil() {return mobil;}
    public void setMobil(String telefon) {this.mobil = mobil;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;    }
}
