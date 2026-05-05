package com.example.Eksamen.models;

public class Kunde {

    private int kundeId;
    private String navn;
    private String telefon;
    private String email;

    public Kunde() {}

    public Kunde(String navn, String telefon, String email) {
        this.navn = navn;
        this.telefon = telefon;
        this.email = email;
    }

    public int getKundeId() {return kundeId;}
    public void setKundeId(int kundeId) {this.kundeId = kundeId;}

    public String getNavn() {return navn;}
    public void setNavn(String navn) {this.navn = navn;}

    public String getTelefon() {return telefon;}
    public void setTelefon(String telefon) {this.telefon = telefon;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;    }
}
