package com.example.reto2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class jugador {

    //Atributos del objeto.
    private int rankIni, posicion, fideID, elo;

    private String nombreJugador, tipoTorneo;

    private boolean gen, cv, hotel;

    //Constructor del objeto.
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

    //GETTERs y SETTERs.
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

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
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

    public String getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(String tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    //Comparador.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        jugador jugador = (jugador) o;
        return fideID == jugador.fideID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fideID);
    }

    //ToString para realizar pruebas.
    @Override
    public String toString() {
        return "jugador{" +
                "rankIni=" + rankIni +
                ", posicion=" + posicion +
                ", nombreJugador='" + nombreJugador + '\'' +
                ", fideID=" + fideID +
                ", elo=" + elo +
                ", gen=" + gen +
                ", cv=" + cv +
                ", hotel=" + hotel +
                ", tipoTorneo='" + tipoTorneo + '\'' +
                '}';
    }
}
