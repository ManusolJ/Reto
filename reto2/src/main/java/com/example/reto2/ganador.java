package com.example.reto2;

public class ganador {

    //Declaracion de atributos del objeto ganador.

    //El rango final del jugador y el importe del premio ganado.
    private int rangoFinal,importePremio;

    //El nombre del jugador.
    private String nombre;

    //Getters y Setters.
    public ganador(int rangoFinal,String nombre,int importePremio){
        this.rangoFinal = rangoFinal;
        this.nombre = nombre;
        this.importePremio = importePremio;
    }

    public int getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(int rangoFinal) {
        this.rangoFinal = rangoFinal;
    }

    public int getImportePremio() {
        return importePremio;
    }

    public void setImportePremio(int importePremio) {
        this.importePremio = importePremio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método toString
    @Override
    public String toString() {
        return "ganador{" +
                "rangoFinal=" + rangoFinal +
                ", importePremio=" + importePremio +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
