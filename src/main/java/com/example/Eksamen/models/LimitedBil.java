package com.example.Eksamen.models;

/** Værdien er ens for alle LimitedBil-objekter, deraf static
* final fordi den aldrig ændrer sig */

public class LimitedBil  extends Bil {
    private static final int LIMITED_DAGE = 150;  //5 måneder, fast

    public LimitedBil() { super(); }

    public LimitedBil(String vognnummer, String stelnummer, String maerke, String model, String udstyrsniveau, int staalpris, int regAfgift, int co2Udledning, String farve, BilStatus status) {
        super(vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, regAfgift, co2Udledning, farve, status);
    }

    /* Returnerer altid 150 - den har ingen variabel periode */
    @Override
    public int getMaxLejePeriodeIDage() {
        return LIMITED_DAGE;
    }

    /**Returnerer strengen "Limited", som matcher forretningens betegnelse og
    * bruges når biltypen skal vises i UI'en eller bruges i rapporter */
    @Override
    public String getAbonnementsType() {
        return "Limited";
    }
}
