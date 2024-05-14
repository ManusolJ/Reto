package com.example.reto2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class HelloControler implements Initializable {

    @FXML
    private Button addB;

    @FXML
    private Label filterLabel;

    @FXML
    private TextField filtertxt;

    @FXML
    private Button modB;

    @FXML
    private Button outB;

    @FXML
    private Button rmB;

    @FXML
    private TableView<jugador> table;

    private ObservableList<jugador> jugadores;

    @FXML
    private TableColumn rankC;

    @FXML
    private TableColumn nomC;

    @FXML
    private TableColumn fedC;

    @FXML
    private TableColumn fideC;

    @FXML
    private TableColumn titleC;

    @FXML
    private TableColumn idnaC;

    @FXML
    private TableColumn infoC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jugadores = FXCollections.observableArrayList();

        jugadores = FXCollections.observableArrayList(
                new jugador("Isabel", 1,"GM","Italia",111,0,"H"),
                new jugador("William", 2, "GM","Alemania",123,1123,"CV"),
                new jugador("Robin", 3, "FM","Alemania",441,0,"CV"),
                new jugador("Emma", 4, "FM","España",671,12,"H"),
                new jugador("Alemania", 5, "GM","Inglaterra",0567,331,"H"));

        this.table.setItems(jugadores);

        this.nomC.setCellValueFactory(new PropertyValueFactory("NomJ"));
        this.rankC.setCellValueFactory(new PropertyValueFactory("startRank"));
        this.titleC.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.idnaC.setCellValueFactory(new PropertyValueFactory("nacID"));
        this.infoC.setCellValueFactory(new PropertyValueFactory("Info"));
        this.fideC.setCellValueFactory(new PropertyValueFactory("fideID"));
        this.fedC.setCellValueFactory(new PropertyValueFactory("federacion"));

        FilteredList<jugador> filteredList = new FilteredList<>(jugadores,b -> true);

        filtertxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(jugador ->{
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(jugador.getNomJ().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }/*else if(jugador.getFederacion().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }else if(jugador.getInfo().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }else if(jugador.getTitulo().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }*/else{
                    return false;
                }
            });
        });

        SortedList<jugador> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedList);
    }

}