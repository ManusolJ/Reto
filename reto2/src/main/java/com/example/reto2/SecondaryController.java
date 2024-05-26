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

    //TODO Arreglar updater, no funciona bien por algún motivo.

    //Botones.
    @FXML
    private Button cancelButton,saveButton;

    //Cajas de seleccion.
    @FXML
    private CheckBox cvCheck,genCheck,hotelCheck;

    //Etiquetas.
    @FXML
    private Label eloLabel,idLabel,nomLabel,rankingLabel;

    //Campos de texto.
    @FXML
    private TextField txtElo,txtID,txtNom,txtRank;

    //Selector de tipo de torneo.
    @FXML
    private ChoiceBox<String> tournamentChoiceBox;

    String [] choices = {"A","B"};

    //Objeto jugador sin definir;
    private jugador jugador;

    //Lista de jugadores sin definir;
    private ObservableList<jugador> jugadores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tournamentChoiceBox.getItems().addAll(choices);
    }

    //TODO Revisar si esto hace falta realmente
    /*public void initAtributtes(ObservableList<jugador> jugadores) {
        this.jugadores = jugadores;
    }*/

    //Pasa los datos del jugador seleccionado.
    public void getAttributes(ObservableList<jugador> jugadores, jugador jugador) {
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
        tournamentChoiceBox.setValue(this.jugador.getTipoTorneo());
    }

    //Devuelve el jugador modificado.
    public jugador getJugador() {
        return jugador;
    }

    //Diferencia entre añadir y modificar.
    public boolean addChecker(){
        return true;
    }

    //Cancela accion de modificacion.
    @FXML
    private void exit(ActionEvent event){
        this.jugador = null;
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void save(ActionEvent event) {
        String nomJugador = this.txtNom.getText();
        String tipoTorneo = this.tournamentChoiceBox.getValue();
        int rankIni = Integer.parseInt(this.txtRank.getText());
        int elo = Integer.parseInt(this.txtElo.getText());
        int fideID = Integer.parseInt(this.txtID.getText());
        boolean gen = this.genCheck.isSelected();
        boolean cv = this.cvCheck.isSelected();
        boolean hotel = this.hotelCheck.isSelected();

        jugador j = new jugador(rankIni, 0, nomJugador, fideID, elo, gen, cv, hotel, tipoTorneo);

        if (this.jugador != null) {
            this.jugador.setNombreJugador(nomJugador);
            this.jugador.setRankIni(rankIni);
            this.jugador.setElo(elo);
            this.jugador.setCv(cv);
            this.jugador.setGen(gen);
            this.jugador.setHotel(hotel);
            this.jugador.setPosicion(0);
            this.jugador.setFideID(fideID);
            this.jugador.setTipoTorneo(tipoTorneo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Se ha modificado correctamente");
            alert.showAndWait();

            Stage stage = (Stage) this.saveButton.getScene().getWindow();
            stage.close();
        } else if(addChecker()) {
            jugador = j;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Se ha añadido un nuevo registro correctamente");
            alert.showAndWait();

            Stage stage = (Stage) this.saveButton.getScene().getWindow();
            stage.close();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("La persona ya existe");
            alert.showAndWait();
        }
    }
}