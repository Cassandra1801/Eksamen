package com.example.Eksamen.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SkadesrapportTest {


    /* Happy flow: en gyldig (positiv) pris skal accepteres,
    og setPris skal gemme værdien korrekt */
    @Test
    void setPris_happyFlow() {
        // Arrange: opret det objekt, testen skal bruge
        Skadesrapport skade = new Skadesrapport();

        // Act: kald den metode, der testes, med gyldigt input
        skade.setPris(BigDecimal.valueOf(1500.0));

        // Assert: verificér at resultatet er som forventet
        assertEquals(BigDecimal.valueOf(1500.0), skade.getPris());
    }

    /* Exception flow: en negativ pris er ugyldig.
        setPris skal kaste IllegalArgumentException,
        så et Skadesrapport-objekt aldrig får en ugyldig pris (fail-fast) */
    @Test
    void setPris_exceptionFlow() {
        // Arrange: opret det objekt, testen skal bruge
        Skadesrapport skade = new Skadesrapport();

        // Assert: verificér at ugyldigt input kaster en exception
        assertThrows(IllegalArgumentException.class, () -> {
            skade.setPris(BigDecimal.valueOf(-100.0));
        });
    }
}


/*  I exception flow-testene er der ikke noget separat Act-trin.
    Det er bevidst — selve handlingen (kaldet der skal fejle) ligger inde i lambdaen,
    som assertThrows udfører. Så Act og Assert smelter sammen til ét
*/