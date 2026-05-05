package com.example.Eksamen.models;

public class Bil {

    private int vognnummer;
    private String stelnummer;
    private String maerke;
    private String model;
    private String farve;
    private BilStatus status;

    public Bil () {}

    public Bil (String stelnummer, String maerke, String model, String farve, BilStatus status) {
        this.stelnummer = stelnummer;
        this.maerke = maerke;
        this.model = model;
        this.farve = farve;
        this.status = status;
    }

    public int getVognnummer () {return vognnummer;}
    public void setVognnummer (int vognnummer) {this.vognnummer = vognnummer;}

    public String getStelnummer() {return stelnummer;}
    public void setStelnummer(String stelnummer) {this.stelnummer = stelnummer;}

    public String getMaerke() {return maerke;}
    public void setMaerke(String maerke) {this.maerke = maerke;}

    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}

    public String getFarve() {return farve;}
    public void setFarve(String farve) {this.farve = farve;}

    public BilStatus getStatus() {return status;}
    public void setStatus(BilStatus status) {this.status = status;}


}
