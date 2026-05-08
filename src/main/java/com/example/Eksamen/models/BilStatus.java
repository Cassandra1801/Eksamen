package com.example.Eksamen.models;


//Dette er en ENUM som bliver brugt til status på bilerne for at holde det samsvarende SQLen. Det er renere og
// effektivere, da det forhindrer typos.
public enum BilStatus {
    INDKØBT,
    LEDIG,
    UDLEJET,
    TILBAGELEVERET,
    SKADET,
    KLAR_TIL_SALG,
    SOLGT
}
