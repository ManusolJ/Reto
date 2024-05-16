CREATE DATABASE ajedrez;
USE ajedrez;

CREATE TABLE jugador (
	ranki INT PRIMARY KEY,
	posicion INT,
	nombre VARCHAR(30),
	fideID INT,
	fide INT,
	cv BOOLEAN,
	hotel BOOLEAN,
    tipo ENUM("A", "B"),
    CONSTRAINT fk_torneo_jugador FOREIGN KEY (tipo) REFERENCES torneo(tipo) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE torneo (
    tipo ENUM("A", "B") PRIMARY KEY
);

CREATE TABLE premios (
	importe FLOAT,
    puesto INT,
    premio ENUM("General", "CV", "Sub2400", "Sub2200", "Sub1800", "Sub1600", "Sub1400", "Alojados125"),
    tipo ENUM("A", "B"),
	PRIMARY KEY (puesto,premio,tipo),
    CONSTRAINT fk_tipo_torneo FOREIGN KEY (tipo) REFERENCES torneo(tipo) ON DELETE CASCADE ON UPDATE CASCADE
);