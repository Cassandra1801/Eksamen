INSERT IGNORE INTO biler (vognnummer, stelnummer, maerke, model, farve, status)
VALUES
    (1001, 'WVWZZZ1JZXW000001', 'Peugeot', '208', 'Sort', 'LEDIG'),
    (1002, 'WVWZZZ1JZXW000002', 'Citroën', 'C3', 'Hvid', 'LEDIG'),
    (1003, 'WVWZZZ1JZXW000003', 'DS', 'DS 3', 'Grå', 'LEDIG'),
    (1004, 'WVWZZZ1JZXW000004', 'Opel', 'Corsa', 'Blå', 'UDLEJET');

INSERT IGNORE INTO kunder (kunde_id, navn, telefon, email)
VALUES
    (1, 'Test Kunde', '12345678', 'test@test.dk'),
    (2, 'Anna Hansen', '87654321', 'anna@test.dk');

INSERT IGNORE INTO lejeaftale (aftale_id, vognnummer, kunde_id, startdato, slutdato, pris)
VALUES
    (1, 1004, 1, '2026-01-01', '2026-06-01', 2495.00);

