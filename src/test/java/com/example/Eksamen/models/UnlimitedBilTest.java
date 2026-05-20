package com.example.Eksamen.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnlimitedBilTest {

    /* Happy flow: en gyldig periode (3-36 måneder) skal accepteres,
        og objektet skal oprettes med rigtig værdi. */
    @Test
    void konstruktoer_gyldigPeriode_opretterObjekt(){
        // Act: opret objekt med gyldigt input (konstruktøren er metoden under test)
        UnlimitedBil bil = new UnlimitedBil(
                "AB1234", "VF7TESTSTEL000001", "Toyota", "Yaris", "Comfort",
                BigDecimal.valueOf(45000), 22000, 130, "Hvid", null, 12);

        // Assert: verificér at perioden blev sat korrekt
        assertEquals(12, bil.getAftaltePeriodeIMaaneder());
    }

    /* Exception flow: en periode under 3 måneder er ugyldig.
        Konstruktøren skal kaste IllegalArgumentException,
        så et ugyldigt objekt aldrig kan eksistere (fail-fast) */
    @Test
    void konstruktoer_periodeForLav_kasterException(){
        // Assert: verificér at ugyldigt input kaster en exception
        assertThrows(IllegalArgumentException.class, () -> {
            new UnlimitedBil(
                    "AB1234", "VF7TESTSTEL000001", "Toyota", "Yaris", "Comfort",
                    BigDecimal.valueOf(45000), 22000, 130, "Hvid", null, 2);
        });
    }
}


/*  I exception flow-testene er der ikke noget separat Act-trin.
    Det er bevidst — selve handlingen (kaldet der skal fejle) ligger inde i lambdaen,
    som assertThrows udfører. Så Act og Assert smelter sammen til ét
*/

/* I happy flow-testen mangler Arrange-trinnet, fordi konstruktøren er metoden under test;
    der er ikke noget objekt at opsætte på forhånd
*/
