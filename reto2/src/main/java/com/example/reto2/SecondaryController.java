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

    // Botones.
    @FXML
    private Button cancelButton, saveButton;

    // Cajas de seleccion.
    @FXML
    private CheckBox cvCheck, genCheck, hotelCheck;

    // Etiquetas.
    @FXML
    private Label eloLabel, idLabel, nomLabel, rankingLabel;

    // Campos de texto.
    @FXML
    private TextField txtElo, txtID, txtNom, txtRank;

    // Selector de tipo de torneo.
    @FXML
    private ChoiceBox<String> tournamentChoiceBox;

    private String[] choices = {"A", "B"};

    // Objeto jugador.
    private jugador jugador;

    // Lista de jugadores.
    private ObservableList<jugador> jugadores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tournamentChoiceBox.getItems().addAll(choices);
    }

    // Pasa los datos del jugador seleccionado.
    public void getAttributes(ObservableList<jugador> jugadores, jugador jugador) {
        this.jugadores = jugadores;
        this.jugador = jugador;
        this.txtNom.setText(this.jugador.getNombreJugador());
        this.txtRank.setText(String.valueOf(this.jugador.getRankIni()));
        this.txtElo.setText(String.valueOf(this.jugador.getElo()));
        this.txtID.setText(String.valueOf(this.jugador.getFideID()));
        genCheck.setSelected(this.jugador.isGen());
        cvCheck.setSelected(this.jugador.isCv());
        hotelCheck.setSelected(this.jugador.isHotel());
        tournamentChoiceBox.setValue(this.jugador.getTipoTorneo());
    }

    // Devuelve el jugador modificado.
    public jugador getJugador() {
        return jugador;
    }

    // Diferencia entre añadir y modificar.
    public boolean addChecker(String fideID) {
        for (jugador j : jugadores) {
            if (String.valueOf(j.getFideID()).equals(fideID)) {
                return false;
            }
        }
        return true;
    }

    // Cancela accion de modificacion.
    @FXML
    private void exit(ActionEvent event) {
        this.jugador = null;
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    // Asigna y guarda los valores del jugador.
    @FXML
    private void save(ActionEvent event) {
        try {
            String nomJugador = this.txtNom.getText();
            String tipoTorneo = this.tournamentChoiceBox.getValue();
            int rankIni = Integer.parseInt(this.txtRank.getText());
            int elo = Integer.parseInt(this.txtElo.getText());
            int fideID = Integer.parseInt(this.txtID.getText());
            boolean gen = this.genCheck.isSelected();
            boolean cv = this.cvCheck.isSelected();
            boolean hotel = this.hotelCheck.isSelected();

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

                showAlert(Alert.AlertType.INFORMATION, "Información", "Se ha modificado correctamente");

            } else if (addChecker(this.txtID.getText())) {
                jugador newJugador = new jugador(rankIni, 0, nomJugador, fideID, elo, gen, cv, hotel, tipoTorneo);
                jugadores.add(newJugador);
                this.jugador = newJugador;

                showAlert(Alert.AlertType.INFORMATION, "Información", "Se ha añadido un nuevo registro correctamente");

            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "La persona ya existe");
            }

            Stage stage = (Stage) this.saveButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, ingrese valores válidos.");
        }
    }
    // Método para mostrar alertas
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}