INSERT IGNORE INTO biler (
    vognnummer,
    stelnummer,
    maerke,
    model,
    udstyrsniveau,
    staalpris,
    reg_afgift,
    co2_udledning,
    farve,
    status,
    bil_type,
    aftalte_periode_i_maaneder
)
VALUES
    ('BA0534', 'VF7SXHMZ6PT123456', 'Citroën', 'C3', 'Shine', 50000, 25000, 150, 'Sort', 'UDLEJET', 'UNLIMITED', 6),
    ('EV2975', 'VF7SXHMZ6PT123174', 'Renault', 'Clio', 'Techno E-tech Hybrid', 22450, 30520, 450, 'Rød', 'UDLEJET', 'UNLIMITED', 6),
    ('AB2097', 'VF7SXANZ6PT654321', 'Peugeot', '208', 'Allure', 146000, 38200, 304, 'Blå', 'UDLEJET', 'UNLIMITED', 6),
    ('TR1234', 'VF7TESTTEST123456', 'Toyota', 'Yaris', 'Comfort',45000, 22000, 130, 'Hvid', 'TILBAGELEVERET', 'UNLIMITED', 3),
    ('BC4321', 'VF7TESTTEST123454', 'Audi', 'A8', 'Comfort',45000, 22000, 130, 'Sort', 'LEDIG', 'UNLIMITED', 3),
    ('SK1001', 'VF7TESTSKADE10001', 'Ford', 'Fiesta', 'Trend',40000, 20000, 120, 'Grå', 'TILBAGELEVERET', 'UNLIMITED', 3),
    ('SK1002', 'VF7TESTSKADE10002', 'Opel', 'Corsa', 'Edition',38000, 19000, 115, 'Blå', 'TILBAGELEVERET', 'UNLIMITED', 3);


INSERT IGNORE INTO kunder (
    kunde_id,
    navn,
    email,
    mobil
)
VALUES
    (1, 'Cass', 'cassmich@gmail.com', '27903986'),
    (2, 'Cam', 'cammich@gmail.com', '27985790'),
    (3, 'Call', 'call@gmail.com', '27958493');


INSERT IGNORE INTO lejeaftaler (
    lejeaftale_id,
    medarbejder_id,
    kunde_id,
    vognnummer,
    lokation,
    start_dato,
    antal_maaneder,
    pris_pr_maaned,
    km_graense
)
VALUES
    (1, '001', 1, 'BA0534', 'København', '2026-01-01', 6, 5000.00, 15000),
    (2, '002', 2, 'EV2975', 'Aarhus', '2026-02-01', 6, 4500.00, 12000),
    (3, '001', 3, 'AB2097', 'Odense', '2026-03-01', 6, 5500.00, 18000),
    (4, '001', 1, 'TR1234', 'København', '2025-01-01', 3, 4000.00, 10000),
    (5, '001', 1, 'SK1001', 'København', '2025-06-01', 3, 4200.00, 12000),
    (6, '001', 1, 'SK1002', 'Aarhus', '2025-07-01', 3, 3900.00, 11000);