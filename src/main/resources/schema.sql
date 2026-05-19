# DROP TABLE IF EXISTS skader;
# DROP TABLE IF EXISTS lejeaftaler;
# DROP TABLE IF EXISTS biler;
# DROP TABLE IF EXISTS kunder;

CREATE TABLE IF NOT EXISTS biler (
                       vognnummer VARCHAR(50) PRIMARY KEY,
                       stelnummer VARCHAR(50) NOT NULL UNIQUE,
                       maerke VARCHAR(50) NOT NULL,
                       model VARCHAR(50) NOT NULL,
                       udstyrsniveau VARCHAR(50) NOT NULL,
                       staalpris DECIMAL(10,2) NOT NULL,
                       reg_afgift INT NOT NULL,
                       co2_udledning INT NOT NULL,
                       farve VARCHAR(50) NOT NULL,
                       status ENUM('INDKØBT','LEDIG','UDLEJET','TILBAGELEVERET','SKADET','KLAR_TIL_SALG','SOLGT','RESERVERET'),

                       bil_type VARCHAR(20) NOT NULL,               -- LIMITED eller UNLIMITED

                       aftalte_periode_i_maaneder INT NULL,    -- Bruges KUN af UnlimitedBil - derfor nullable

                       CONSTRAINT chk_bil_type CHECK ( bil_type IN ('LIMITED', 'UNLIMITED')),
                       CONSTRAINT chk_unlimited_periode CHECK (
                            (bil_type = 'LIMITED' AND aftalte_periode_i_maaneder IS NULL ) OR
                            (bil_type = 'UNLIMITED' AND aftalte_periode_i_maaneder BETWEEN 3 AND 36)
                            )
);

CREATE TABLE IF NOT EXISTS kunder (
                        kunde_id INT PRIMARY KEY AUTO_INCREMENT,
                        navn VARCHAR(100) NOT NULL,
                        email VARCHAR(50) NOT NULL UNIQUE,
                        mobil VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS lejeaftaler (
                             lejeaftale_id INT PRIMARY KEY AUTO_INCREMENT,
                             medarbejder_id VARCHAR(50) NOT NULL,
                             kunde_id INT NOT NULL,
                             vognnummer VARCHAR(50) NOT NULL,
                             lokation VARCHAR(200),
                             start_dato DATE NOT NULL,
                             antal_maaneder INT NOT NULL,
                             pris_pr_maaned DECIMAL(10,2) NOT NULL,
                             km_graense INT NOT NULL,
                             FOREIGN KEY (kunde_id) REFERENCES kunder(kunde_id),
                             FOREIGN KEY (vognnummer) REFERENCES biler(vognnummer)
);

CREATE TABLE IF NOT EXISTS skader (
                        skade_id INT PRIMARY KEY AUTO_INCREMENT,
                        vognnummer VARCHAR(50) NOT NULL,
                        lejeaftale_id INT NOT NULL,
                        medarbejder_id VARCHAR(50) NOT NULL,
                        dato DATE NOT NULL,
                        beskrivelse VARCHAR(255) NOT NULL,
                        pris DECIMAL(10,2) NOT NULL,
                        FOREIGN KEY (vognnummer) REFERENCES biler(vognnummer),
                        FOREIGN KEY (lejeaftale_id) REFERENCES lejeaftaler(lejeaftale_id)
);