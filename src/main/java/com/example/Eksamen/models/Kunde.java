package com.example.Eksamen.models;

public class Kunde {

    private int kundeId;
    private String navn;
    private String mobil;
    private String email;

    public Kunde() {}

    public Kunde(String navn, String mobil, String email) {
        this.navn = navn;
        this.mobil = mobil;
        this.email = email;
    }

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public String getNavn() {return navn;}
    public void setNavn(String navn) {this.navn = navn;}

    public String getMobil() {return mobil;}
    public void setMobil(String mobil) {this.mobil = mobil;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;    }
}
