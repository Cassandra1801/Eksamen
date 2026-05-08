INSERT IGNORE INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning,farve, status)
VALUES
    ('BA0534','VF7SXHMZ6PT123456','Citroën','C3','Shine',50.000,25.000,150,'Sort','KLAR_TIL_SALG'),
    ('EV2975','VF7SXHMZ6PT123174', 'Renault', 'Clio','techno E-tech Hybrid',22.450,30.520,450,'Rød','UDLEJET'),
    ('AB2097','VF7SXANZ6PT654321','Peugeeot',208,'Normal',146.000,38.200,304,'Allure','INDKØBT');

INSERT IGNORE INTO kunder(kunde_id, navn, email, mobil)
VALUES
    (01,'Cass','cassmich@gmail.com','279039'),
    (02,'Cam','cammich@gmail.com','279857'),
    (03,'Call','cah@gmail.com','2795849');


INSERT IGNORE INTO lejeaftaler (lejeaftale_Id, medarbejder_Id, kunde_Id, vognnummer, lokation, startdato, slutdato, pris_pr_maaned, km_graense)
VALUES
    (1, 'M001', 1, 1, 'København', '2026-01-01', '2026-06-01', 5000.00, 15000),
    (2, 'M002', 2, 2, 'Aarhus', '2026-02-01', '2026-07-01', 4500.00, 12000),
    (3, 'M001', 3, 3, 'Odense', '2026-03-01', '2026-08-01', 5500.00, 18000);

