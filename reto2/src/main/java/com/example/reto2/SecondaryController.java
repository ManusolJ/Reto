package com.example.reto2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondaryController implements Initializable {

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox cvCheck;

    @FXML
    private Label eloLabel;

    @FXML
    private CheckBox genCheck;

    @FXML
    private CheckBox hotelCheck;

    @FXML
    private Label idLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label rankingLabel;

    @FXML
    private Button saveButton;

    @FXML
    private TextField txtElo;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtRank;

    private jugador jugador;

    private ObservableList<jugador> jugadores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initAtributtes(ObservableList<jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void initAtributtes(ObservableList<jugador> jugadores, jugador jugador) {
        this.jugadores = jugadores;
        this.jugador = jugador;
        this.txtNom.setText(this.jugador.getNombreJugador());
        this.txtRank.setText(String.valueOf(this.jugador.getRankIni()));
        this.txtElo.setText(String.valueOf(this.jugador.getElo()));
        this.txtID.setText(String.valueOf(this.jugador.getFideID()));
        if (this.jugador.isGen()) {
            genCheck.fire();
        }
        if (this.jugador.isCv()) {
            cvCheck.fire();
        }
        if (this.jugador.isHotel()) {
            hotelCheck.fire();
        }
    }

    public jugador getJugador() {
        return jugador;
    }

    @FXML
    private void exit(ActionEvent event){
        this.jugador = null;
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event) {
        String nomJugador = this.txtNom.getText();
        String tipoTorneo = jugador.getTipoTorneo();
        int rankIni = Integer.parseInt(this.txtRank.getText());
        int posicion = jugador.getPosicion();
        int elo = Integer.parseInt(this.txtElo.getText());
        int fideID = Integer.parseInt(this.txtID.getText());
        boolean gen;
        boolean cv;
        boolean hotel;
        if (this.genCheck.isSelected()) {
            gen = true;
        } else {
            gen = false;
        }
        if (this.cvCheck.isSelected()) {
            cv = true;
        } else {
            cv = false;
        }
        if (this.hotelCheck.isSelected()) {
            hotel = true;
        } else {
            hotel = false;
        }

        jugador j = new jugador(rankIni, posicion, nomJugador, fideID, elo, gen, cv, hotel, tipoTorneo);

        if (this.jugador != null) {

            this.jugador.setNombreJugador(nomJugador);
            this.jugador.setRankIni(rankIni);
            this.jugador.setElo(elo);
            this.jugador.setCv(cv);
            this.jugador.setGen(gen);
            this.jugador.setHotel(hotel);
            this.jugador.setPosicion(posicion);
            this.jugador.setFideID(fideID);
            this.jugador.setTipoTorneo(tipoTorneo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Se ha modificado correctamente");
            alert.showAndWait();

            Stage stage = (Stage) this.saveButton.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("La persona ya existe");
            alert.showAndWait();
        }
    }


}
