CREATE DATABASE ajedrez DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

USE ajedrez;

CREATE TABLE torneo (
    nombreTorneo VARCHAR(50),
    tipoTorneo ENUM('A', 'B') PRIMARY KEY
);

CREATE TABLE jugador (
    rankIni INT,
    posicion INT,
    nombreJugador VARCHAR(120),
    fideID INT UNIQUE,
    ELO INT,
    gen BOOLEAN DEFAULT TRUE,
    CV BOOLEAN,
    hotel BOOLEAN,
    tipoTorneo ENUM('A', 'B'),
    PRIMARY KEY (rankIni , tipoTorneo),
    CONSTRAINT fk_TorneoJugador FOREIGN KEY (tipoTorneo)
        REFERENCES torneo (tipoTorneo)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE premio (
    nombrePremio VARCHAR(100),
    gen BOOLEAN,
    CV BOOLEAN,
    hotel BOOLEAN,
    ELO INT,
    tipoTorneo ENUM('A', 'B'),
    PRIMARY KEY (nombrePremio , tipoTorneo),
    CONSTRAINT fk_PremioTorneo FOREIGN KEY (tipoTorneo)
        REFERENCES torneo (tipoTorneo)
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ganaPremio (
    nombrePremio VARCHAR(100),
    posicion INT,
    importe INT,
    fideID INT,
    tipoTorneo ENUM('A', 'B'),
    idPremio INT UNIQUE auto_increment,
    prioridad INT DEFAULT 0,
    PRIMARY KEY (tipoTorneo , posicion , importe , nombrePremio),
    CONSTRAINT fk_torneoGanar FOREIGN KEY (tipoTorneo)
        REFERENCES torneo (tipoTorneo)
        ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO torneo values
("OPEN A","A"),
("OPEN B","B");

INSERT INTO premio values
("GENERAL",TRUE,FALSE,FALSE,NULL,"A"),
("GENERAL",TRUE,FALSE,FALSE,NULL,"B"),
("COMUNIDAD VALENCIANA",FALSE,TRUE,FALSE,NULL,"A"),
("COMUNIDAD VALENCIANA",FALSE,TRUE,FALSE,NULL,"B"),
("HOTEL",FALSE,FALSE,TRUE,NULL,"A"),
("HOTEL",FALSE,FALSE,TRUE,NULL,"B"),
("SUB2400",FALSE,FALSE,FALSE,2400,"A"),
("SUB2200",FALSE,FALSE,FALSE,2200,"A"),
("SUB1800",FALSE,FALSE,FALSE,1800,"B"),
("SUB1600",FALSE,FALSE,FALSE,1600,"B"),
("SUB1400",FALSE,FALSE,FALSE,1400,"B");

insert into ganaPremio (nombrePremio,posicion,importe,tipoTorneo) values
("GENERAL",1,2300,"A"),
("GENERAL",2,1200,"A"),
("GENERAL",3,650,"A"),
("GENERAL",4,550,"A"),
("GENERAL",5,500,"A"),
("GENERAL",6,400,"A"),
("GENERAL",7,300,"A"),
("GENERAL",8,250,"A"),
("GENERAL",9,200,"A"),
("GENERAL",10,150,"A"),
("GENERAL",11,100,"A"),
("GENERAL",12,100,"A"),
("HOTEL", 1,125,"A"),
("HOTEL", 2,125,"A"),
("HOTEL", 3,125,"A"),
("HOTEL", 4,125,"A"),
("HOTEL", 5,125,"A"),
("HOTEL", 6,125,"A"),
("HOTEL", 7,125,"A"),
("HOTEL", 8,125,"A"),
("HOTEL", 9,125,"A"),
("HOTEL", 10,125,"A"),
("HOTEL", 11,125,"A"),
("HOTEL", 12,125,"A"),
("HOTEL", 13,125,"A"),
("HOTEL", 14,125,"A"),
("HOTEL", 15,125,"A"),
("HOTEL", 16,125,"A"),
("HOTEL", 17,125,"A"),
("HOTEL", 18,125,"A"),
("HOTEL", 19,125,"A"),
("HOTEL", 20,125,"A"),
("SUB2400",1,225,"A"),
("SUB2400",2,150,"A"),
("SUB2400",3,120,"A"),
("SUB2400",4,100,"A"),
("SUB2200",1,150,"A"),
("SUB2200",2,100,"A"),
("COMUNIDAD VALENCIANA",1,500,"A"),
("COMUNIDAD VALENCIANA",2,400,"A"),
("COMUNIDAD VALENCIANA",3,300,"A"),
("COMUNIDAD VALENCIANA",4,200,"A"),
("COMUNIDAD VALENCIANA",5,100,"A"),
("GENERAL",1,2300,"B"),
("GENERAL",2,1200,"B"),
("GENERAL",3,650,"B"),
("GENERAL",4,550,"B"),
("GENERAL",5,500,"B"),
("GENERAL",6,400,"B"),
("GENERAL",7,300,"B"),
("GENERAL",8,250,"B"),
("GENERAL",9,200,"B"),
("GENERAL",10,150,"B"),
("GENERAL",11,100,"B"),
("GENERAL",12,100,"B"),
("HOTEL", 1,125,"B"),
("HOTEL", 2,125,"B"),
("HOTEL", 3,125,"B"),
("HOTEL", 4,125,"B"),
("HOTEL", 5,125,"B"),
("HOTEL", 6,125,"B"),
("HOTEL", 7,125,"B"),
("HOTEL", 8,125,"B"),
("HOTEL", 9,125,"B"),
("HOTEL", 10,125,"B"),
("HOTEL", 11,125,"B"),
("HOTEL", 12,125,"B"),
("HOTEL", 13,125,"B"),
("HOTEL", 14,125,"B"),
("HOTEL", 15,125,"B"),
("HOTEL", 16,125,"B"),
("HOTEL", 17,125,"B"),
("HOTEL", 18,125,"B"),
("HOTEL", 19,125,"B"),
("HOTEL", 20,125,"B"),
("COMUNIDAD VALENCIANA",1,500,"B"),
("COMUNIDAD VALENCIANA",2,400,"B"),
("COMUNIDAD VALENCIANA",3,300,"B"),
("COMUNIDAD VALENCIANA",4,200,"B"),
("COMUNIDAD VALENCIANA",5,100,"B"),
("SUB1800",1,150,"B"),
("SUB1800",2,100,"B"),
("SUB1600",1,150,"B"),
("SUB1600",2,100,"B"),
("SUB1400",1,150,"B"),
("SUB1400",2,100,"B");

CREATE VIEW PremiosOptadosPorOpenA AS
    SELECT 
        nombreJugador, GROUP_CONCAT(Nombrepremio, '') AS premios
    FROM
        premio p
            JOIN
        jugador j ON j.tipoTorneo = p.tipoTorneo
    WHERE
        (((p.gen = TRUE AND j.gen = TRUE)
            OR (p.cv = TRUE AND j.cv = TRUE)
            OR (p.hotel = TRUE AND j.hotel = TRUE)
            OR (p.ELO > j.ELO))
            AND j.tipoTorneo LIKE 'A')
    GROUP BY nombreJugador;

CREATE VIEW PremiosOptadosPorOpenB AS
    SELECT 
        nombreJugador, GROUP_CONCAT(Nombrepremio, '') AS premios
    FROM
        premio p
            JOIN
        jugador j ON j.tipoTorneo = p.tipoTorneo
    WHERE
        (((p.gen = TRUE AND j.gen = TRUE)
            OR (p.cv = TRUE AND j.cv = TRUE)
            OR (p.hotel = TRUE AND j.hotel = TRUE)
            OR (p.ELO > j.ELO))
            AND j.tipoTorneo LIKE 'B')
    GROUP BY nombreJugador;

CREATE VIEW Ganadores_general_A AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'General' AND gp.tipoTorneo LIKE 'A';

CREATE VIEW Ganadores_Sub2400 AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'Sub2400' AND gp.tipoTorneo LIKE 'A';

CREATE VIEW Ganadores_Sub2200 AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'Sub2200' AND gp.tipoTorneo LIKE 'A';

CREATE VIEW Ganadores_cv_A AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'COMUNIDAD VALENCIANA' AND gp.tipoTorneo LIKE 'A';

CREATE VIEW Ganadores_hotel_A AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'HOTEL' AND gp.tipoTorneo LIKE 'A';

CREATE VIEW Ganadores_general_B AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'General' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW Ganadores_Sub1800 AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'Sub1800' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW Ganadores_Sub1600 AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'Sub1600' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW Ganadores_Sub1400 AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'Sub1400' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW Ganadores_cv_B AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'COMUNIDAD VALENCIANA' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW Ganadores_hotel_B AS
SELECT gp.posicion,nombreJugador,importe FROM ganaPremio gp join jugador j on gp.fideID = j.fideID WHERE nombrePremio like 'HOTEL' AND gp.tipoTorneo LIKE 'B';

CREATE VIEW resultado_A AS
SELECT nombrePremio as Categoria,gp.posicion AS Puesto,nombreJugador,importe from ganaPremio gp JOIN jugador j on gp.fideID = j.fideID where gp.tipoTorneo like 'A' order by prioridad,nombrePremio;

CREATE VIEW resultado_B AS
SELECT nombrePremio as Categoria,gp.posicion AS Puesto,nombreJugador,importe from ganaPremio gp JOIN jugador j on gp.fideID = j.fideID where gp.tipoTorneo like 'B' order by prioridad,nombrePremio;