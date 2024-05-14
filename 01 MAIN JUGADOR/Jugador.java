public class Jugador {
    private int ranki;
    private int posicion;
    private String nombre;
    private int fideID;
    private int fide;
    private boolean cv;
    private boolean hotel;
    private String tipo;

    public Jugador (int ranki, int posicion, String nombre, int fideID, int fide , boolean cv, boolean hotel, String tipo) {
        this.ranki = ranki;
        this.posicion = posicion;
        this.nombre = nombre;
        this.fideID = fideID;
        this.fide = fide;
        this.cv = cv;
        this.hotel = hotel;
        this.tipo = tipo;
    }

    public int getRanki() {
        return ranki;
    }

    public void setRanki(int ranki) {
        this.ranki = ranki;
    }


    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFideID() {
        return fideID;
    }

    public void setFideID(int fideID) {
        this.fideID = fideID;
    }

    public int getFide() {
        return fide;
    }

    public void setFide(int fide) {
        this.fide = fide;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

