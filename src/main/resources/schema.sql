CREATE TABLE IF NOT EXISTS biler (
    vognnummer INT PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS kunder (
    kunde_id INT PRIMARY KEY AUTO_INCREMENT,
    navn VARCHAR(100) NOT NULL,
    telefon VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lejeaftale (
    aftale_id INT PRIMARY KEY AUTO_INCREMENT,
    vognnummer INT NOT NULL,
    kunde_id INT NOT NULL,
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