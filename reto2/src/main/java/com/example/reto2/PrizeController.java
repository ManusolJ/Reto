package com.example.reto2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrizeController implements Initializable {

    //TODO hay que hacer todas las funciones de esta parte y quizas pensar un poco mejor el diseño de la pantalla de premios

    @FXML
    private Label filterLabel;

    @FXML
    ChoiceBox<String> prizeChoiceBox,tournChoiceBox;

    String [] prizeChoiceA = {"General","Comunidad Valencia","Alojamiento","Sub2400","Sub2200"};

    String [] prizeChoiceB = {"General","Comunidad Valencia","Alojamiento","Sub1800","Sub1600","Sub1400"};

    String [] tournChoice = {"A","B"};

    @FXML
    private TextField txtFilter;

    @FXML
    private Button exitBtn;

    @FXML
    private TableView<jugador> table;

    private ObservableList<jugador> jugadores = FXCollections.observableArrayList();

    @FXML
    private TableColumn<jugador,String> posCol,nameCol,prizeCol;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tournChoiceBox.getItems().addAll(tournChoice);
        prizeChoiceBox.getItems().addAll(prizeChoiceA);

        tournChoiceBox.setValue("A");

        prizeChoiceBox.setValue("General");

        tournChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(tournChoiceBox.getValue().equals("A")){
                    prizeChoiceBox.getItems().addAll(prizeChoiceA);
                }else{
                    prizeChoiceBox.getItems().addAll(prizeChoiceB);
                }
            }
        });
    }

    public void endAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("selectionScreen.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();

            stage.setScene(scene);

            stage.setTitle("Selection Screen");

            stage.show();

            Stage mystage = (Stage) filterLabel.getScene().getWindow();

            mystage.close();
        }catch (IOException e){
            e.getMessage();
        }
    }
}
