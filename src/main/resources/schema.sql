# DROP TABLE IF EXISTS skader;
# DROP TABLE IF EXISTS skadesrapporter;
# DROP TABLE IF EXISTS lejeaftaler;
# DROP TABLE IF EXISTS biler;
# DROP TABLE IF EXISTS kunder;

CREATE TABLE IF NOT EXISTS biler (
                       vognnummer INT PRIMARY KEY,
                       stelnummer VARCHAR(50) NOT NULL UNIQUE,
                       maerke VARCHAR(50) NOT NULL,
                       model VARCHAR(50) NOT NULL,
                       udstyrsniveau VARCHAR(50) NOT NULL,
                       staalpris DECIMAL NOT NULL,
                       reg_afgift INT NOT NULL,
                       CO2_udledning INT NOT NULL,
                       status ENUM('INDKØBT','LEDIG','UDLEJET','TILBAGELEVERET','SKADET','KLAR_TIL_SALG','SOLGT')
);

CREATE TABLE IF NOT EXISTS kunder (
                        kunde_Id INT PRIMARY KEY AUTO_INCREMENT,
                        navn VARCHAR(100) NOT NULL,
                        email VARCHAR(50) NOT NULL UNIQUE,
                        mobil VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lejeaftaler (
                             lejeaftale_Id INT PRIMARY KEY AUTO_INCREMENT,
                             medarbejder_Id VARCHAR(50) NOT NULL,
                             kunde_Id INT NOT NULL,
                             vognnummer INT NOT NULL,
                             lokation VARCHAR(200),
                             startdato DATE NOT NULL,
                             slutdato DATE NOT NULL,
                             pris_pr_maaned DECIMAL(10,2) NOT NULL,
                             km_graense INT NOT NULL,
                             FOREIGN KEY (kunde_Id) REFERENCES kunder(kunde_Id),
                             FOREIGN KEY (vognnummer) REFERENCES biler(vognnummer)
);

CREATE TABLE IF NOT EXISTS skader (
                        skade_Id INT PRIMARY KEY AUTO_INCREMENT,
                        vognnummer INT NOT NULL,
                        lejeaftale_Id INT NOT NULL,
                        medarbejder_Id INT NOT NULL,
                        dato DATE NOT NULL,
                        beskrivelse VARCHAR(255) NOT NULL,
                        pris DECIMAL(10,2) NOT NULL,
                        FOREIGN KEY (vognnummer) REFERENCES biler(vognnummer),
                        FOREIGN KEY (lejeaftale_Id) REFERENCES lejeaftaler(lejeaftale_Id)
);