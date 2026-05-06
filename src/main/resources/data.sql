INSERT IGNORE INTO bil (bil_Id,nummerplade, stelnummer, maerke, model, udstyrsniveau, staalpris, reg_afgift, CO2_udledning, status)
VALUES
    (01,'EF889','hej','Audi','A8','style',200,500,21,'INDKØBT'),
    (02,'EF899','neJ','Audi','A7','pretty',220,600,22,'UDLEJET'),
    (03,'EF999','JA','Audi','A5','Mmeh',2200,5200,212,'INDKØBT');

INSERT IGNORE INTO kunde(kunde_id, navn, email, mobil)
VALUES
    (01,'Casws','cassmich@gmail.com','279039'),
    (02,'Cam','cammich@gmail.com','279857'),
    (03,'Cah','cah@gmail.com','2795849');


