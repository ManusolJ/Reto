/*TO DO COMPROBAR PRIMARY KEY EN NOMBREPREMIO Y TERMINAR TABLA DE GANAR PREMIO. */

CREATE DATABASE ajedrez DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

USE ajedrez;

CREATE TABLE torneo(
	nombreTorneo varchar(50),
    tipoTorneo ENUM("A","B") PRIMARY KEY);

CREATE TABLE jugador(
	rankIni INT,
    posicion INT,
    nombreJugador VARCHAR(120),
    fideID INT UNIQUE,
    ELO INT,
    gen BOOLEAN DEFAULT TRUE,
    CV BOOLEAN,
    hotel BOOLEAN,
    tipoTorneo ENUM("A","B"),
    PRIMARY KEY (rankIni,tipoTorneo),
    CONSTRAINT fk_TorneoJugador FOREIGN KEY (tipoTorneo) REFERENCES torneo(tipoTorneo) ON DELETE CASCADE ON UPDATE CASCADE);
    
CREATE TABLE premio(
	nombrePremio VARCHAR(100) /*PRIMARY KEY*/,
    gen BOOLEAN,
    CV BOOLEAN,
    hotel BOOLEAN,
    ELO INT,
    tipoTorneo ENUM("A","B"),
    CONSTRAINT fk_PremioTorneo FOREIGN KEY (tipoTorneo) REFERENCES torneo(tipoTorneo) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE ganaPremio(
	nombrePremio varchar(100),
    posicion int,
	importe int,
    tipoTorneo enum("A","B"),
    PRIMARY KEY(tipoTorneo,posicion,nombrePremio),
    CONSTRAINT fk_premioGanar FOREIGN KEY (nombrePremio) REFERENCES premio(nombrePremio) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_torneoGanar FOREIGN KEY(tipoTorneo) REFERENCES torneo(tipoTorneo) ON DELETE CASCADE ON UPDATE CASCADE);
    
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

INSERT INTO jugador values
(1,1,"FULGENCIO",20500,2200,TRUE,TRUE,FALSE,"A");

SELECT fideID,nombrePremio from premio p join jugador j on j.tipoTorneo = p.tipoTorneo where (p.gen = true or p.cv = true or p.ELO > j.ELO) AND p.tipoTorneo like "B";

update jugador set elo = 1600;

INSERT INTO jugador values
(1,1,"FULGENCIO",20501,1600,TRUE,TRUE,FALSE,"B");

Select * from jugador;