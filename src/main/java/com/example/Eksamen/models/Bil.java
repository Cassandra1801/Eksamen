package com.example.Eksamen.models;

import java.math.BigDecimal;

/* Abstrakt tvinger alle subklasser til at implementere dem og giver polymorfi */
public abstract class Bil {

    private String vognnummer;
    private String stelnummer;
    private String maerke;
    private String model;
    private String udstyrsniveau;
    private BigDecimal staalpris;
    private int regAfgift;
    private int co2Udledning;
    private String farve;
    private BilStatus status;

    /* RowMapper i BilRepo instantierer subklasserne uden argumenter og
       derefter sætter felter via setters */
    public Bil () {}

    public Bil (String vognnummer, String stelnummer, String maerke, String model, String udstyrsniveau, BigDecimal staalpris, int regAfgift, int co2Udledning, String farve, BilStatus status) {
        this.vognnummer= vognnummer;
        this.stelnummer = stelnummer;
        this.maerke = maerke;
        this.model = model;
        this.udstyrsniveau = udstyrsniveau;
        this.staalpris = staalpris;
        this.regAfgift = regAfgift;
        this.co2Udledning = co2Udledning;
        this.farve = farve;
        this.status = status;
    }


    /* Hver biltype har sin egen regel for hvor længe den må lejes */
    public abstract int getMaxLejePeriodeIDage();

    /* Returnere den forretningsmæssige betegnelse ("Limited"/"Unlimited"), som bruges i UI og rapporter */
    public abstract String getAbonnementsType();

    public String getVognnummer() {return vognnummer;}
    public void setVognnummer(String vognnummer) {this.vognnummer = vognnummer;}

    public String getStelnummer() {return stelnummer;}
    public void setStelnummer(String stelnummer) {this.stelnummer = stelnummer;}

    public String getMaerke() {return maerke;}
    public void setMaerke(String maerke) {this.maerke = maerke;}

    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}

    public String getUdstyrsniveau() {return udstyrsniveau;}
    public void setUdstyrsniveau(String udstyrsniveau) {this.udstyrsniveau = udstyrsniveau;}

    public BigDecimal getStaalpris() {return staalpris;}
    public void setStaalpris(BigDecimal staalpris) {this.staalpris = staalpris;}

    public int getRegAfgift() {return regAfgift;}
    public void setRegAfgift(int regAfgift) {this.regAfgift = regAfgift;}

    public int getCo2Udledning() {return co2Udledning;}
    public void setCo2Udledning(int co2Udledning) {this.co2Udledning = co2Udledning;}

    public String getFarve() {return farve;}
    public void setFarve(String farve) {this.farve = farve;}

    public BilStatus getStatus() {return status;}
    public void setStatus(BilStatus status) {this.status = status;}
}
