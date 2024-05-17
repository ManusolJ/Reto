import java.lang.reflect.Array;

public class jugador {

    private int rankInic;  // 0 inicial
    private int posicion;  // 0  fin
    private String nombreJugador; // 4 fin
    private int fideID;  // 6 inicial
    private int ELO;  // 13 fin
    private boolean general;
    private boolean cv;  // 16 fin
    private boolean hotel;
    private String tipoTorneo;

    public jugador(int rankInic, int posicion, String nombreJugador, int fideID, int ELO, boolean general, boolean cv, boolean hotel, String tipoTorneo) {

        this.rankInic = rankInic;
        this.posicion = posicion;
        this.nombreJugador = nombreJugador;
        this.fideID = fideID;
        this.ELO = ELO;
        this.general = general;
        this.cv = cv;
        this.hotel = hotel;
        this.tipoTorneo = tipoTorneo;

    }


    public int getRankID() {
        return rankInic;
    }

    public void setRankID(int rankID) {
        this.rankInic = rankID;
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

    public int getELO() {
        return ELO;
    }

    public void setELO(int ELO) {
        this.ELO = ELO;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
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





}
