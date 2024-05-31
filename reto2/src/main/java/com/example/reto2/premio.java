package com.example.reto2;

public class premio {

    // Declaración de atributos del objeto premio.

    //Nombre del premio y tipo de torneo.
    private String nombrePremio, tipoTorneo;

    //Posición del premio, importe, fideID del jugador que ha ganado el premio, ID único del premio, prioridad que tiene el premio.
    private int posicion,importe,fideID,idPremio,prioridad;

   //Constructor.
    public premio(String nombrePremio,int posición,int importe,int fideID,String tipotorneo,int idPremio,int prioridad){
        this.nombrePremio = nombrePremio;
        this.posicion = posicion;
        this.importe = importe;
        this.fideID = fideID;
        this.tipoTorneo = tipotorneo;
        this.idPremio = idPremio;
        this.prioridad = prioridad;
    }

    // Getters y Setters.
    public String getNombrePremio() {
        return nombrePremio;
    }

    public void setNombrePremio(String nombrePremio) {
        this.nombrePremio = nombrePremio;
    }

    public String getTipoTorneo() {
        return tipoTorneo;
    }

    public void setTipoTorneo(String tipoTorneo) {
        this.tipoTorneo = tipoTorneo;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public int getFideID() {
        return fideID;
    }

    public void setFideID(int fideID) {
        this.fideID = fideID;
    }

    public int getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(int idPremio) {
        this.idPremio = idPremio;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    // Método toString
    @Override
    public String toString() {
        return "premio{" +
                "nombrePremio = '" + nombrePremio + '\'' +
                ", tipoTorneo = '" + tipoTorneo + '\'' +
                ", posición = " + posicion +
                ", importe = " + importe +
                ", fideID = " + fideID +
                ", idPremio = " + idPremio +
                ", prioridad = " + prioridad +
                '}';
    }
}
