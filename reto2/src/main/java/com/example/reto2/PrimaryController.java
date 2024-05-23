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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


public class PrimaryController implements Initializable{
    //TODO mirad cualquier "todo" que haya por ahi. Mirad tambien si podeis hacer metodos de funciones recurrentes.

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

        //AVISAD SI MODIFICAIS ESTO
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
        //Por todos los dioses avisadme si modificais esta funcion.
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

        //TODO Manejar error al usar la 'X' de la  ventana en vez del boton de salir.
    }

    //AÑADE JUGADOR A BASE DE DATOS
    public void addAction(ActionEvent event) throws IOException, SQLException {

        //Cargamos la vista secundaria.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondaryView.fxml"));

        Parent secondaryScene = loader.load();

        //Cargamos el controlador de la vista.
        SecondaryController controlador = loader.getController();

        //Creamos y cargamos la escena.
        Scene scene = new Scene(secondaryScene);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Datos");
        stage.setScene(scene);
        stage.showAndWait();

        controlador.addChecker();

        //Cargamos los datos introducidos a un objeto de jugador.
        j = controlador.getJugador();

        if(j != null) {
            //Preparamos la query.
            querysql = "INSERT INTO jugador(rankIni,posicion,NombreJugador,fideID,ELO,gen,CV,hotel,tipoTorneo) VALUES (?,?,?,?,?,?,?,?,?)";

            ps = cnx.prepareStatement(querysql);

            //Cargamos los parametros del objeto a los parametros de la query.
            ps.setInt(1, j.getRankIni());
            ps.setInt(2, j.getPosicion());
            ps.setString(3, j.getNombreJugador());
            ps.setInt(4, j.getFideID());
            ps.setInt(5, j.getElo());
            ps.setBoolean(6, j.isGen());
            ps.setBoolean(7, j.isCv());
            ps.setBoolean(8, j.isHotel());
            ps.setString(9, j.getTipoTorneo());

            //Ejecutamos query y cerramos statement.
            ps.executeUpdate();
            ps.close();
        }
        //Refrescamos tabla.
        loadData();
    }

    //HACE DELETE SOBRE JUGADOR SELECCIONADO EN TABLA
    public void deleteAction(ActionEvent event)throws SQLException{
        //Creo un jugador de la seleccion de la tabla.
        j = this.table.getSelectionModel().getSelectedItem();
        //Si no hay jugador seleccionado, da error.
        if (j == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Delete Action Error.");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        }else{
            //Preparamos la query.
            querysql = "DELETE FROM jugador WHERE fideID = ?";

            ps = cnx.prepareStatement(querysql);

            //Usamos el fideID del jugador seleccionado para realizar la accion de borrado.
            ps.setInt(1,j.getFideID());

            ps.executeUpdate();

            ps.close();
            //Refrescamos la vista
            refreshTable();
        }
    }

    //REALIZA MODIFICACION SOBRE JUGADOR SELECCIONADO
    public void modifyAction(ActionEvent event){
        //Creo un jugador de la seleccion de la tabla.
        j = this.table.getSelectionModel().getSelectedItem();
        //Si no hay jugador seleccionado salta error.
        if (j == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Modify Action Error.");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        }else {
            try {
                //Cargo vista secundaria para modificar datos.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("secondaryView.fxml"));

                Parent secondaryScene = loader.load();

                //Cargo controlador de la vista secundaria.
                SecondaryController controlador = loader.getController();

                //Le doy los datos del jugador seleccionado y de la lista de jugadores ya creada.
                controlador.getAttributes(jugadores, j);

                //Creamos y mostramos la escena.
                Scene scene = new Scene(secondaryScene);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Modificacion de Datos");
                stage.setScene(scene);
                stage.showAndWait();

                //Pido los datos del jugador modficado.
                jugador jModified = controlador.getJugador();

                //Se llama al metodo que hace la modificacion en la base de datos.
               try {
                   if (jModified != null) {
                       update(jModified, j.getFideID());
                       this.table.refresh();
                   }
               }catch (SQLException e){
                   System.out.println("Update Error!");
               }

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //ELIGE ARCHIVO CON DATOS Y LOS VUELCA EN BASE DE DATOS
    public void addDataAction(ActionEvent event) throws IOException,SQLException {

        //Ventana de elegir archivo mediante explorador de windows.
        FileChooser fc = new FileChooser();

        //Nombre de la ventana.
        fc.setTitle("Elegir archivo CSV.");

        Stage stage = new Stage();

        //Creamos un objeto de archivo, y lo definimos como el archivo elegido mediante el file chooser.
        File selectedFile = fc.showOpenDialog(stage);

        //Creamos una lista vacia.
        ArrayList<jugador> list;

        //Llamamos al metodo que lee el archivo seleccionado y lo transforma en objetos de jugador, lo cuales insertamos en la lista.
        list = datos(selectedFile);

        //Creamos un iterador de la lista que contiene los jugadores.
        Iterator<jugador> it = list.iterator();

        //Creamos la query de sql para insertar en la base de datos.
        querysql = "INSERT INTO jugador(rankIni,posicion,NombreJugador,fideID,ELO,gen,CV,hotel,tipoTorneo) VALUES (?,?,?,?,?,?,?,?,?)";

        ps = cnx.prepareStatement(querysql);
        //Iteramos por la lista de jugadores, añadiendolos a la base de datos.
        while(it.hasNext()) {
            jugador j = it.next();
            ps.setInt(1,j.getRankIni());
            ps.setInt(2,j.getPosicion());
            ps.setString(3,j.getNombreJugador());
            ps.setInt(4,j.getFideID());
            ps.setInt(5,j.getElo());
            ps.setBoolean(6,j.isGen());
            ps.setBoolean(7,j.isCv());
            ps.setBoolean(8,j.isHotel());
            ps.setString(9,j.getTipoTorneo());
            ps.executeUpdate();
        }
        //Cerramos el statement y cargamos los datos a la vista.
        ps.close();
        loadData();
    }

    //LEER DATOS DE ARCHIVO CSV
    public static ArrayList<jugador> datos(File file) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        boolean cv = false;
        boolean hotel = false;
        //Queremos un contador para saltar la primera linea de el archivo CSV.
        //TODO buscar mejor forma de saltar las lineas del archivo que no queremos leer.
        int contador = 0;
        ArrayList<jugador> list = new ArrayList<>();

        //Creamos un bucle, el cual itera mientras el lector tenga lineas para leer, que crea objetos de jugador con la informacion leida y los añade a una lista.
        while ((line = br.readLine()) != null) {
            String[] str = line.split(",");
            if (str.length == 11) {
                if (str[10].contains("H")) {
                    hotel = true;
                }
                if (str[10].contains("CV")) {
                    cv = true;
                }
                if(contador != 0) {
                        jugador j = new jugador(Integer.parseInt(str[0]), 0, str[2].replace("\"", "") + str[3].replace("\"", ""), Integer.parseInt(str[7]), Integer.parseInt(str[5]), true, cv, hotel, "A");
                        list.add(j);
                }
            } else {
                if(contador != 0) {
                    jugador j = new jugador(Integer.parseInt(str[0]), 0, str[2].replace("\"","") + str[3].replace("\"",""), Integer.parseInt(str[7]), Integer.parseInt(str[5]), true, false, false, "A");
                    list.add(j);
                }
            }
            contador++;
            hotel = false;
            cv = false;
        }
        return list;
    }

    //UPDATE SOBRE BASE DE DATOS
    public  void update(jugador j, int fideID) throws SQLException{
        querysql = "UPDATE jugador SET rankIni = ?, posicion = ?, nombreJugador = ?, fideID = ?, ELO = ?, gen = ?, CV = ?, hotel = ?, tipoTorneo = ? WHERE fideID = ?";

        ps = cnx.prepareStatement(querysql);

        ps.setInt(1,j.getRankIni());
        ps.setInt(2,j.getPosicion());
        ps.setString(3,j.getNombreJugador());
        ps.setInt(4,j.getFideID());
        ps.setInt(5,j.getElo());
        ps.setBoolean(6,j.isGen());
        ps.setBoolean(7,j.isCv());
        ps.setBoolean(8,j.isHotel());
        ps.setString(9,j.getTipoTorneo());
        ps.setInt(10,fideID);
        ps.executeUpdate();
        ps.close();
    }
}

