package com.example.Eksamen.models;


// Enum for bilstatus. Værdierne matcher status-feltet i databasen.
public enum BilStatus {
    INDKØBT,
    LEDIG,
    UDLEJET,
    TILBAGELEVERET,
    SKADET,
    KLAR_TIL_SALG,
    SOLGT,
    RESERVERET
}
