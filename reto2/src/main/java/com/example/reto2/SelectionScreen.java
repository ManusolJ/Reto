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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectionScreen implements Initializable {

    //Botones.
    @FXML
    private Button clsButton,datosJugadores,premiosGanados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Cierre de aplicacion.
    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    //Carga la escena de datos de jugadores.
    public void openData(ActionEvent event) throws IOException {
        try {
            //Carga la vista.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primaryView.fxml"));

            Parent primaryScene = loader.load();

            //Cargo el controlador de la vista.
            PrimaryController controller = loader.getController();

            //Creo y asocio la escena.
            Scene scene = new Scene(primaryScene);

            Stage stage = new Stage();

            stage.setScene(scene);

            stage.setTitle("Datos del Torneo");

            stage.show();

            //Accion que tiene realizar al cerrar.
            stage.setOnCloseRequest(e -> controller.endAction());

            //Cierro la escena actual.
            Stage myStage = (Stage) this.datosJugadores.getScene().getWindow();

            myStage.close();

        }catch (Exception e){
            Logger.getLogger(SelectionScreen.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    //Carga la escena de premios ganados.
    public void openPrizeScreen(ActionEvent event) throws IOException {
        //Cargo la vista.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prizeView.fxml"));

        Parent primaryScene = loader.load();

        //Cargo el controlador.
        PrizeController controller = loader.getController();

        //Creo y asocio la escena.
        Scene scene = new Scene(primaryScene);

        Stage stage = new Stage();

        stage.setTitle("Ganadores del Torneo");

        stage.setScene(scene);

        stage.show();

        //Accion a realizar al cerrar la escena.
        stage.setOnCloseRequest(e -> controller.endAction());

        //Cierro la escena actual.
        Stage myStage = (Stage) this.premiosGanados.getScene().getWindow();

        myStage.close();
    }
}
