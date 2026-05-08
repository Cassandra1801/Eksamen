INSERT IGNORE INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning, status)
VALUES
    (01,'EF889','hej','Audi','A8',4,200,500,'INDKØBT'),
    (02,'EF899','neJ','Audi','A7',5,220,600,'UDLEJET'),
    (03,'EF999','JA','Audi','A5',6,2200,5200,'INDKØBT');

INSERT IGNORE INTO kunder(kunde_id, navn, email, mobil)
VALUES
    (01,'Casws','cassmich@gmail.com','279039'),
    (02,'Cam','cammich@gmail.com','279857'),
    (03,'Cah','cah@gmail.com','2795849');


INSERT IGNORE INTO lejeaftaler (lejeaftale_Id, medarbejder_Id, kunde_Id, vognnummer, lokation, startdato, slutdato, pris_pr_maaned, km_graense)
VALUES
    (1, 'M001', 1, 1, 'København', '2026-01-01', '2026-06-01', 5000.00, 15000),
    (2, 'M002', 2, 2, 'Aarhus', '2026-02-01', '2026-07-01', 4500.00, 12000),
    (3, 'M001', 3, 3, 'Odense', '2026-03-01', '2026-08-01', 5500.00, 18000);

