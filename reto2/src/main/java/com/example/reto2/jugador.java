package com.example.reto2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class jugador {

    // Declaración de atributos de la tabla jugador

    // Ranking Inicial, Posicion Final, fideID, Elo.
    private int rankIni,posicion,fideID,elo;

    // Nombre del jugador, Torneo al que pertenece.
    private String nombreJugador,tipoTorneo;

    // Marcador para ver si opta a general, comunidad valenciana, alojamiento.
    private boolean gen,cv,hotel;

    //Constructor de jugador para realizar update sobre la posicion final.
    public jugador(int posicion,int rankIni, String nombreJugador) {
        this.posicion = posicion;
        this.rankIni = rankIni;
        this.nombreJugador = nombreJugador;
    }

    //Constructor de jugador inicial.
    public jugador(int rankIni,int posicion,String nombreJugador,int fideID,int elo,boolean gen,boolean cv,boolean hotel,String tipoTorneo){
        this.rankIni = rankIni;
        this.posicion = posicion;
        this.nombreJugador = nombreJugador;
        this.fideID = fideID;
        this.elo = elo;
        this.gen = gen;
        this.cv = cv;
        this.hotel = hotel;
        this.tipoTorneo = tipoTorneo;
    }

    // Getters y Setters
    public int getRankIni() {
        return rankIni;
    }

    public void setRankIni(int rankIni) {
        this.rankIni = rankIni;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getFideID() {
        return fideID;
    }

    public void setFideID(int fideID) {
        this.fideID = fideID;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(String tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    public boolean isGen() {
        return gen;
    }

    public void setGen(boolean gen) {
        this.gen = gen;
    }

    public boolean isCv() {
        return cv;
    }

    public void setCv(boolean cv) {
        this.cv = cv;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    // Determina si hay igualdad
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        jugador jugador = (jugador) o;
        return fideID == jugador.fideID;
    }

    // Identificador
    @Override
    public int hashCode() {
        return Objects.hashCode(fideID);
    }

    // Método toString
    @Override
    public String toString() {
        return "jugador{" +
                "rankIni=" + rankIni +
                ", posicion=" + posicion +
                ", fideID=" + fideID +
                ", elo=" + elo +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", tipoTorneo='" + tipoTorneo + '\'' +
                ", gen=" + gen +
                ", cv=" + cv +
                ", hotel=" + hotel +
                '}';
    }
}