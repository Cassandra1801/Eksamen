CREATE TABLE IF NOT EXISTS biler (
    vognnummer INT PRIMARY KEY,
    stelnummer VARCHAR(50),
    maerke VARCHAR(50),
    model VARCHAR(50),
    farve VARCHAR(50),
    status ENUM(
        'INDKØBT',
        'LEDIG',
        'UDLEJET',
        'TILBAGELEVERET',
        'SKADET',
        'KLAR_TIL_SALG',
        'SOLGT'
    )
);

CREATE TABLE IF NOT EXISTS kunder (
    kunde_id INT PRIMARY KEY AUTO_INCREMENT,
    navn VARCHAR(50),
    telefon VARCHAR(50),
    email VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS lejeaftale (
    aftale_id INT PRIMARY KEY AUTO_INCREMENT,
    vognnummer INT,
    kunde_id INT,
    startdato DATE,
    slutdato DATE,
    pris DECIMAL(10,2),
    FOREIGN KEY (vognnummer)
        REFERENCES biler(vognnummer),
    FOREIGN KEY (kunde_id)
        REFERENCES kunder(kunde_id)
);

CREATE TABLE IF NOT EXISTS skader (
    skade_id INT PRIMARY KEY AUTO_INCREMENT,
    vognnummer INT,
    beskrivelse VARCHAR(50),
    pris DECIMAL(10,2),
    dato DATE,
    FOREIGN KEY (vognnummer)
        REFERENCES biler(vognnummer)
);