package com.example.reto2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class PrimaryController implements Initializable{

    //CONEXION A BASE DE DATOS
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/ajedrez";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }

    //BOTONES
    @FXML
    private Button addButton, modifyButton, exitButton, removeButton, importButton;

    //ETIQUETAS
    @FXML
    private Label filterLabel;

    //CAMPOS DE TEXTO
    @FXML
    private TextField filtertxt;

    //TABLA Y LISTA DE OBJETOS
    @FXML
    private TableView<jugador> table;

    private ObservableList<jugador> jugadores = FXCollections.observableArrayList();

    //COLUMNAS DE LA TABLA
    @FXML
    private  TableColumn rankIniCol, NombreJugadorCol, eloCol, fideIDCol, infoCol;

    //SELECCIONADOR Y CAMPOS DE SELECCION
    @FXML
    private ChoiceBox<String> cBox;

    String [] choices = {"Open A","Open B"};

    //VARIABLE RECURRENTES
    String querysql = "";

    PreparedStatement ps = null;

    Connection cnx = null;

    ResultSet rs = null;

    jugador j = null;

    //METODO DE INICIALIZACION
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cBox.getItems().addAll(choices);

            cBox.setValue("Open A");

            loadData();

            FilteredList<jugador> filteredList = new FilteredList<>(jugadores, b -> true);

            filtertxt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(jugador -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (jugador.getNombreJugador().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                        return true;
                        //TODO FILTRAR POR OTROS CAMPOS
                    }/*else if(jugador.getFederacion().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }else if(jugador.getInfo().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }else if(jugador.getTitulo().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                    return true;
                }*/ else {
                        return false;
                    }
                });

                SortedList<jugador> sortedList = new SortedList<>(filteredList);

                sortedList.comparatorProperty().bind(table.comparatorProperty());

                table.setItems(sortedList);
            });

            cBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    try {
                        loadData();
                    }catch (SQLException e) {
                        System.out.println("CHOICE ERROR!");
                    }

                }
            });
        }catch (SQLException e){
            System.out.println("SQL ERROR!");
        }
    }

    //CARGA DATOS DE LA BASE A LISTA DE JUGADORES Y LOS AÑADE A LA TABLA
    public void refreshTable() throws SQLException{
        jugadores.clear();

        if(cBox.getValue().contains("Open A")) {
            querysql = "SELECT * FROM jugador WHERE tipoTorneo like 'A'";

        }else{
            querysql = "SELECT * FROM jugador WHERE tipoTorneo like 'B'";
        }

        ps = cnx.prepareStatement(querysql);
        rs = ps.executeQuery();

        while(rs.next()){
            jugadores.add(new jugador(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getBoolean(6),
                    rs.getBoolean(7),
                    rs.getBoolean(8),
                    rs.getString(9))
            );
        }
        table.setItems(jugadores);
    }

    //CARGA LOS DATOS A CADA CELDA
    public void loadData() throws SQLException{
        cnx = getConnexion();

        refreshTable();

        this.NombreJugadorCol.setCellValueFactory(new PropertyValueFactory<jugador,String>("nombreJugador"));
        this.rankIniCol.setCellValueFactory(new PropertyValueFactory<jugador,Integer>("rankIni"));
        this.eloCol.setCellValueFactory(new PropertyValueFactory<jugador,Integer>("elo"));
        this.fideIDCol.setCellValueFactory(new PropertyValueFactory<jugador,Integer>("fideID"));
        this.infoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<jugador, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<jugador, String> p) {
                String s = " ";
                if (p.getValue().isGen()) {
                    s = s + "Gen ";
                }
                if(p.getValue().isCv()){
                    s = s + " CV";
                }
                if(p.getValue().isHotel()){
                    s = s + " H";
                }
                return new SimpleStringProperty(s);
            }
        });
    }

    //TERMINA PROCESO DE VENTANA Y TE LLEVA A SELECTIONSCREEN
    public void endAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("selectionScreen.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);

        stage.setTitle("Selection Screen");

        stage.show();

        Stage mystage = (Stage) filterLabel.getScene().getWindow();

        mystage.close();
    }

    //AÑADE JUGADOR A BASE DE DATOS
    public void addAction(ActionEvent event) throws IOException, SQLException {
        jugador j = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondaryView.fxml"));

        Parent secondaryScene = loader.load();

        SecondaryController controlador = loader.getController();

        Scene scene = new Scene(secondaryScene);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Datos");
        stage.setScene(scene);
        stage.showAndWait();

        j = controlador.getJugador();

        querysql = "INSERT INTO jugador (rankIni,posicion,nombreJugador,fideID,ELO,gen,CV,hotel,tipoTorneo values (?,?,?,?,?,?,?,?,?)";
        //TODO TERMINAR INSERCION DE JUGADOR EN DB
    }

    //HACE DELETE SOBRE JUGADOR SELECCIONADO EN TABLA
    public void deleteAction(ActionEvent event)throws SQLException{
        j = this.table.getSelectionModel().getSelectedItem();

        if (j == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Delete Action Error.");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        }else{
            querysql = "DELETE FROM jugador WHERE fideID = ?";

            ps = cnx.prepareStatement(querysql);

            ps.setInt(1,j.getFideID());

            ps.executeUpdate();

            ps.close();

            refreshTable();
        }
    }

    //REALIZA MODIFICACION SOBRE JUGADOR SELECCIONADO
    public void modifyAction(ActionEvent event){
        jugador j = this.table.getSelectionModel().getSelectedItem();

        if (j == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Modify Action Error.");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("secondaryView.fxml"));

                Parent secondaryScene = loader.load();

                SecondaryController controlador = loader.getController();

                controlador.initAtributtes(jugadores, j);

                Scene scene = new Scene(secondaryScene);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Datos");
                stage.setScene(scene);
                stage.showAndWait();

                jugador jModified = controlador.getJugador();

                if (jModified != null) {
                    j = jModified;
                    this.table.refresh();
                }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
        //TODO REPASAR COMO FUNCIONA CODIGO
    }

    //ELIGE ARCHIVO CON DATOS Y LOS VUELCA EN BASE DE DATOS
    public void addDataAction(ActionEvent event){
        FileChooser fc = new FileChooser();

        fc.setTitle("Elegir archivo CSV.");

        Stage stage = new Stage();

        fc.showOpenDialog(stage);

        //TODO TERMINAR INSERCION DE DATOS EN DB
    }
}

