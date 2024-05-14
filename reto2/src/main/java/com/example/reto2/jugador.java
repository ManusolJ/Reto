package com.example.reto2;

import java.util.Objects;
import java.util.Scanner;

public class jugador {

    public static Scanner sc = new Scanner(System.in);
    /*
    public static Connection cnx;
    static{
        try{
            cnx = DBConnectio.getConnexion();
        }catch (SQLException e){
            Throw new RuntimeException(e);
        }
    }
     */

    String nomJ;

    int startRank;

    String titulo;

    String federacion;

    int fideID;

    int nacID;

    String info;

    public jugador(String nomJ, int startRank, String titulo, String federacion, int fideID, int nacID,String info) {
        this.nomJ = nomJ;
        this.startRank = startRank;
        this.titulo = titulo;
        this.federacion = federacion;
        this.fideID = fideID;
        this.nacID = nacID;
        this.info = info;
    }

    public static Scanner getSc() {
        return sc;
    }

    public static void setSc(Scanner sc) {
        jugador.sc = sc;
    }

    public String getNomJ() {
        return nomJ;
    }

    public void setNomJ(String nomJ) {
        this.nomJ = nomJ;
    }

    public int getStartRank() {
        return startRank;
    }

    public void setStartRank(int startRank) {
        this.startRank = startRank;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFederacion() {
        return federacion;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
    }

    public int getFideID() {
        return fideID;
    }

    public void setFideID(int fideID) {
        this.fideID = fideID;
    }

    public int getNacID() {
        return nacID;
    }

    public void setNacID(int nacID) {
        this.nacID = nacID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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
}
