package com.example.Eksamen.models;

public class UnlimitedBil extends Bil {
    private int aftaltePeriodeIMaaneder;   //3-36 måneder, variabel

    public UnlimitedBil() { super(); }

    public UnlimitedBil(String vognnummer, String stelnummer, String maerke, String model,
                        String udstyrsniveau, int staalpris, int regAfgift, int co2Udledning,
                        String farve, BilStatus status, int aftalePeriodeIMaaneder) {
        super (vognnummer, stelnummer, maerke, model, udstyrsniveau,
                staalpris, regAfgift, co2Udledning, farve, status);
        if (aftaltePeriodeIMaaneder < 3 || aftaltePeriodeIMaaneder > 36) {
            throw new IllegalArgumentException("Unlimited skal være 3-36 måneder");
        }
        this.aftaltePeriodeIMaaneder = aftaltePeriodeIMaaneder;
    }

    @Override
    public int getMaxLejePeriodeIDage() {
        return aftaltePeriodeIMaaneder * 30;
    }

    @Override
    public String getAbonnementsType(){
        return "Unlimited";
    }

    public int getAftaltePeriodeIMaaneder() {return aftaltePeriodeIMaaneder; }
    public void setAftaltePeriodeIMaaneder(int v) {
        this.aftaltePeriodeIMaaneder = v;
    }
}
