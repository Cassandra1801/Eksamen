INSERT IGNORE INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning,farve, status,bil_type)
VALUES
    ('BA0534','VF7SXHMZ6PT123456','Citroën','C3','Shine',50000,25000,150,'Sort','UDLEJET','UNLIMITED'),
    ('EV2975','VF7SXHMZ6PT123174', 'Renault', 'Clio','Techno E-tech Hybrid',22450,30520,450,'Rød','UDLEJET','UNLIMITED'),
    ('AB2097','VF7SXANZ6PT654321','Peugeot',208,'Allure',146000,38200,304,'Blå','UDLEJET', 'UNLIMITED');

INSERT IGNORE INTO kunder(kunde_id, navn, email, mobil)
VALUES
    (1,'Cass','cassmich@gmail.com','27903986'),
    (2,'Cam','cammich@gmail.com','27985790'),
    (3,'Call','call@gmail.com','27958493');


INSERT IGNORE INTO lejeaftaler (lejeaftale_Id, medarbejder_Id, kunde_Id, vognnummer, lokation, startdato, slutdato, pris_pr_maaned, km_graense)
VALUES
    (1, 'M001', 1, 'BA0534', 'København', '2026-01-01', '2026-06-01', 5000.00, 15000),
    (2, 'M002', 2, 'EV2975', 'Aarhus', '2026-02-01', '2026-07-01', 4500.00, 12000),
    (3, 'M001', 3, 'AB2097', 'Odense', '2026-03-01', '2026-08-01', 5500.00, 18000);

