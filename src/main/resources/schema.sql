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
    kunde_id INT PRIMARY KEY AUTO_INCREMENT,
    navn VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    mobil VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lejeaftale (
    lejeaftale_id INT PRIMARY KEY AUTO_INCREMENT,
    medarbejder_Id INT NOT NULL,
    kunde_id INT NOT NULL,
    bik_Id INT NOT NULL,
    lokation VARCHAR(200),
    startdato DATE NOT NULL,
    slutdato DATE NOT NULL,
    pris DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (vognnummer)
        REFERENCES biler(vognnummer),
    FOREIGN KEY (kunde_id)
        REFERENCES kunder(kunde_id)
);

CREATE TABLE IF NOT EXISTS skader (
    skade_id INT PRIMARY KEY AUTO_INCREMENT,
    aftale_id INT NOT NULL,
    beskrivelse VARCHAR(255) NOT NULL,
    pris DECIMAL(10,2) NOT NULL,
    total_pris DECIMAL(10,2) NOT NULL,
    dato DATE NOT NULL,
    FOREIGN KEY (aftale_id)
        REFERENCES lejeaftale(aftale_id)
);

DROP TABLE lejeaftale;
DROP TABLE skader;
DROP TABLE kunder;
DROP TABLE biler;