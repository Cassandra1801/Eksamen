package com.example.Eksamen.models;

public class Bil {

    private int bilId;
    private int nummerplade;
    private String stelnummer;
    private String maerke;
    private String model;
    private String udstyrsniveau;
    private int staalpris;
    private int regAfgift;
    private int co2Udledning;
    private BilStatus status;

    public Bil () {}

    public Bil (String stelnummer, String maerke, String model, String farve, BilStatus status) {
        this.stelnummer = stelnummer;
        this.maerke = maerke;
        this.model = model;
        this.status = status;
    }

    public int getBilId() {return bilId;}
    public void setBilId(int bilId) {this.bilId = bilId;}

    public int getNummerplade () {return nummerplade;}
    public void setNummerplade (int nummerplade) {this.nummerplade = nummerplade;}

    public String getStelnummer() {return stelnummer;}
    public void setStelnummer(String stelnummer) {this.stelnummer = stelnummer;}

    public String getMaerke() {return maerke;}
    public void setMaerke(String maerke) {this.maerke = maerke;}

    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}

    public String getUdstyrsniveau() {return udstyrsniveau;}
    public void setUdstyrsniveau(String udstyrsniveau) {this.udstyrsniveau = udstyrsniveau;}

    public int getStaalpris() {return staalpris;}
    public void setStaalpris(int staalpris) {this.staalpris = staalpris;}

    public int getRegAfgift() {return regAfgift;}
    public void setRegAfgift(int regAfgift) {this.regAfgift = regAfgift;}

    public int getCo2Udledning() {return co2Udledning;}
    public void setCo2Udledning(int co2Udledning) {this.co2Udledning = co2Udledning;}

    public BilStatus getStatus() {return status;}
    public void setStatus(BilStatus status) {this.status = status;}


}
