CREATE TABLE IF NOT EXISTS bil (
    bil_Id INT PRIMARY KEY,
    nummerplade INT NOT NULL,
    stelnummer VARCHAR(50) NOT NULL UNIQUE,
    maerke VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    udstyrsniveau VARCHAR(50) NOT NULL,
    staalpris DECIMAL NOT NULL,
    reg_afgift INT NOT NULL,
    CO2_udledning INT NOT NULL,
    status ENUM(
        'INDKØBT',
        'LEDIG',
        'UDLEJET',
        'TILBAGELEVERET',
        'SKADET',
        'KLAR_TIL_SALG',
        'SOLGT'
    ) NOT NULL
);


CREATE TABLE IF NOT EXISTS kunde (
    kunde_Id INT PRIMARY KEY AUTO_INCREMENT,
    navn VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    mobil VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lejeaftale (
    lejeaftale_Id INT PRIMARY KEY AUTO_INCREMENT,
    medarbejder_Id INT NOT NULL,
    kunde_Id INT NOT NULL,
    bil_Id INT NOT NULL,
    lokation VARCHAR(200),
    startdato DATE NOT NULL,
    slutdato DATE NOT NULL,
    pris_pr_maaned DECIMAL(10,2) NOT NULL,
    km_graense INT NOT NULL,
    FOREIGN KEY (medarbejder_Id)
        REFERENCES medarbejder(medarbejder_Id),
    FOREIGN KEY (kunde_Id)
        REFERENCES kunde(kunde_Id),
    FOREIGN KEY (bil_Id)
        REFERENCES bil(bil_Id)
);

CREATE TABLE IF NOT EXISTS medarbejder (
    medarbejder_Id INT PRIMARY KEY AUTO_INCREMENT,
    navn VARCHAR(50),
    kodeord VARCHAR(255),
    email VARCHAR(255),
    rolle ENUM (
        'DATAREGISTRERING',
        'SKADE OG UDBEDRING',
        'FORRETNINGSUDVIKLER'
        )
);

CREATE TABLE IF NOT EXISTS skadesrapport (
    skade_Id INT NOT NULL,
    bil_Id INT NOT NULL,
    lejeaftale_Id INT NOT NULL,
    medarbejder_Id INT NOT NULL,
    dato DATE NOT NULL,
    beskrivelse VARCHAR(255) NOT NULL,
    pris DECIMAL(10,2) NOT NULL,
    total_pris DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (bil_Id)
        REFERENCES bil(bil_Id),
    FOREIGN KEY (lejeaftale_Id)
        REFERENCES lejeaftale(lejeaftale_Id),
    FOREIGN KEY (medarbejder_Id)
        REFERENCES medarbejder(medarbejder_Id)
);

