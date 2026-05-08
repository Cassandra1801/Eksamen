package com.example.Eksamen.models;

public class LimitedBil  extends Bil {
    private static final int LIMITED_DAGE = 150;  //5 måneder, fast

    public LimitedBil() { super(); }

    public LimitedBil(String vognnummer, String stelnummer, String maerke, String model, String udstyrsniveau, int staalpris, int regAfgift, int co2Udledning, String farve, BilStatus status) {
        super(vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, regAfgift, co2Udledning, farve, status);
    }

    @Override
    public int getMaxLejePeriodeIDage() {
        return LIMITED_DAGE;
    }

    @Override
    public String getAbonnementsType() {
        return "Limited";
    }
}
