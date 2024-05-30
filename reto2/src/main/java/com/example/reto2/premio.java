package com.example.reto2;

public class premio {

    private String categoria, tipoTorneo;;

    private int posicion,importe,fideID;

    public premio(String categoria,String tipotorneo,int posicion,int importe, int fideID){
        this.importe = importe;
        this.categoria = categoria;
        this.posicion = posicion;
        this.tipoTorneo = tipotorneo;
        this.fideID = fideID;
    }

    public String getCategoria() {
        return categoria;
    }

    public void SetCategoria(String nombrePremio) {
        this.categoria = nombrePremio;
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

    @Override
    public String toString() {
        return "premio{" +
                "categoria='" + categoria + '\'' +
                ", tipoTorneo='" + tipoTorneo + '\'' +
                ", posicion=" + posicion +
                ", importe=" + importe +
                ", fideID=" + fideID +
                '}';
    }
}
