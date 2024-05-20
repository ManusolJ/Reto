package com.example.reto2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectionScreen implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button clsButton;

    @FXML
    private Button datosJugadores;

    @FXML
    private Button premiosGanados;

    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    public void openData(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primaryView.fxml"));

        Parent primaryScene = loader.load();

        PrimaryController controller = loader.getController();

        Scene scene = new Scene(primaryScene);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Cringe");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            try {
                controller.endAction();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Stage myStage = (Stage) this.datosJugadores.getScene().getWindow();
        myStage.close();
    }
}
