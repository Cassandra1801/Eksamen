INSERT IGNORE INTO biler (vognnummer, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning, status)
VALUES
    (1001, 'WVWZZZ1JZXW000001', 'Peugeot', '208', 'Style', 20, 222, 23,'LEDIG'),
    (1002, 'WVWZZZ1JZXW000002', 'Citroën', 'C3', 'Blank', 23, 12, 40,'SKADET'),
    (1003, 'WVWZZZ1JZXW000003', 'DS', 'DS 3', 'Shine', 12, 10, 60,'INDKØBT'),
    (1004, 'WVWZZZ1JZXW000004', 'Opel', 'Corsa',  'Style', 14, 12, 12,'LEDIG');

INSERT IGNORE INTO kunder (kunde_id, navn, telefon, email)
VALUES
    (1, 'Test Kunde', '12345678', 'test@test.dk'),
    (2, 'Anna Hansen', '87654321', 'anna@test.dk');

INSERT IGNORE INTO lejeaftale (aftale_id, vognnummer, kunde_id, startdato, slutdato, pris)
VALUES
    (1, 1004, 1, '2026-01-01', '2026-06-01', 2495.00);

