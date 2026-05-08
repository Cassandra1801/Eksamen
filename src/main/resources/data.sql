INSERT IGNORE INTO biler
(vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning, status)
VALUES
(1001, 'STEL12345', 'Toyota', 'Yaris', 'Base', 150000, 30000, 95, 'LEDIG'),
(1002, 'STEL12346', 'Volkswagen', 'Golf', 'Comfortline', 220000, 45000, 120, 'UDLEJET'),
(1003, 'STEL12347', 'Tesla', 'Model 3', 'Long Range', 350000, 0, 0, 'LEDIG'),
(1004, 'STEL12348', 'BMW', '320i', 'Sport', 400000, 80000, 140, 'SKADET');

INSERT IGNORE INTO kunder
(kunde_Id, navn, email, mobil)
VALUES
(1, 'Anders Jensen', 'anders@mail.dk', '12345678'),
(2, 'Mette Hansen', 'mette@mail.dk', '87654321'),
(3, 'Lars Nielsen', 'lars@mail.dk', '11223344');

INSERT IGNORE INTO lejeaftaler
(lejeaftale_Id, medarbejder_Id, kunde_Id, vognnummer, lokation, startdato, slutdato, pris_pr_maaned, km_graense)
VALUES
(1, 'EMP001', 1, 1002, 'København', '2026-05-01', '2026-08-01', 5000.00, 1500),
(2, 'EMP002', 2, 1004, 'Aarhus', '2026-04-15', '2026-07-15', 6500.00, 2000);

INSERT IGNORE INTO skadesrapporter
(skade_Id, vognnummer, lejeaftale_Id, medarbejder_Id, dato, beskrivelse, pris)
VALUES
(1, 1004, 2, 'EMP002', '2026-05-05', 'Ridser på højre dør', 2500.00),
(2, 1004, 2, 'EMP002', '2026-05-05', 'Bule i bagkofanger', 4000.00);