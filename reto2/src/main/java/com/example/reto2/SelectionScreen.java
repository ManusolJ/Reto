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

    // Método de inicialización que se llama después de cargar el archivo FXML.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // Referencias a los componentes definidos en el archivo FXML.
    @FXML
    private Button clsButton, datosJugadores, premiosGanados;

    // Método para cerrar la aplicación cuando se presiona el botón de cerrar.
    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    // Método para abrir la vista primaria cuando se presiona el botón correspondiente.
    public void openData(ActionEvent event) throws IOException {
        try {
            // Cargar el archivo FXML para la vista primaria.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primaryView.fxml"));

            // Cargar la vista primaria.
            Parent primaryScene = loader.load();

            // Obtener el controlador asociado con la vista primaria.
            PrimaryController controller = loader.getController();

            // Configurar una nueva escena y escenario para la vista primaria.
            Scene scene = new Scene(primaryScene);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Hacer que la nueva ventana sea modal.
            stage.setTitle("Datos del Torneo"); // Establecer el título de la nueva ventana.
            stage.setScene(scene); // Establecer la escena de la nueva ventana.
            stage.show(); // Mostrar la nueva ventana.

            // Configurar la acción al cerrar la ventana.
            stage.setOnCloseRequest(e -> controller.endAction());

            // Cerrar la ventana actual.
            Stage myStage = (Stage) this.datosJugadores.getScene().getWindow();
            myStage.close();

        }catch (Exception e){
            Logger.getLogger(SelectionScreen.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Método para abrir la pantalla de premios cuando se presiona el botón correspondiente.
    public void openPrizeScreen(ActionEvent event) throws IOException {
        // Cargar el archivo FXML para la vista de premios.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("prizeView.fxml"));

        // Cargar la vista de premios.
        Parent primaryScene = loader.load();

        // Obtener el controlador asociado con la vista de premios.
        PrizeController controller = loader.getController();

        // Configurar una nueva escena y escenario para la vista de premios.
        Scene scene = new Scene(primaryScene);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Hacer que la nueva ventana sea modal.
        stage.setTitle("Ganadores del Torneo"); // Establecer el título de la nueva ventana.
        stage.setScene(scene); // Establecer la escena de la nueva ventana.
        stage.show(); // Mostrar la nueva ventana.

        // Configurar la acción al cerrar la ventana.
        stage.setOnCloseRequest(e -> controller.endAction());

        // Cerrar la ventana actual.
        Stage myStage = (Stage) this.datosJugadores.getScene().getWindow();
        myStage.close();
    }
}
