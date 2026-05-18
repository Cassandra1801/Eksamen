package com.example.Eksamen.models;

/* Lejeperioden er variabel - fra 3 op til 36 måneder */

/**  Det er "aftaltePeriodeIMaaneder der retfærdiggøre brugen af arv,
 * i stedet for at have en Bil-klasse med "if-type"-check */

public class UnlimitedBil extends Bil {
    private int aftalePeriodeIMaaneder;   //3-36 måneder, variabel

    public UnlimitedBil() { super(); }

    /**  Kontruktøren validerer perioden og kaster en IllegalArgumentException,
     * hvis værdien er udenfor intervallet.
     * Dette gør at et ugyldigt UnlimitedBil-objekt aldrig kan eksistrerer (fail-fast princip) */
    public UnlimitedBil(String vognnummer, String stelnummer, String maerke, String model,
                        String udstyrsniveau, int staalpris, int regAfgift, int co2Udledning,
                        String farve, BilStatus status, int aftalePeriodeIMaaneder) {
        super (vognnummer, stelnummer, maerke, model, udstyrsniveau,
                staalpris, regAfgift, co2Udledning, farve, status);
        if (aftalePeriodeIMaaneder < 3 || aftalePeriodeIMaaneder > 36) {
            throw new IllegalArgumentException("Unlimited skal være 3-36 måneder");
        }

        this.aftalePeriodeIMaaneder = aftalePeriodeIMaaneder;
    }

    /**  Beregner periode dynamisk ud fra det konkrete antal måneder,
    * modsat LimitedBil hvor den er konstant.
    * Polymorfi: samme metodekald, forskellig implementering afhængigt af det faktiske objekt */
    @Override
    public int getMaxLejePeriodeIDage() {
        return aftalePeriodeIMaaneder * 30;
    }

    @Override
    public String getAbonnementsType(){
        return "Unlimited";
    }

    public int getAftalePeriodeIMaaneder() {return aftalePeriodeIMaaneder; }
    public void setAftalePeriodeIMaaneder(int v) {
        this.aftalePeriodeIMaaneder = v;
    }
}
