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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PrimaryController implements Initializable{

    //Conexión a base de datos.
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://ajedrezalpha.clt77wc4mhcd.us-east-1.rds.amazonaws.com:3306/ajedrez";
        String user = "root";
        String password = "RootRoot1";
        return DriverManager.getConnection(url, user, password);
    }

    //Botones.
    @FXML
    private Button addButton, modifyButton, exitButton, removeButton, importButton, exportButton;

    //Etiquetas.
    @FXML
    private Label filterLabel;

    //Campo de texto.
    @FXML
    private TextField filtertxt;

    //Tabla y lista de jugadores.
    @FXML
    private TableView<jugador> table;

    private ObservableList<jugador> jugadores = FXCollections.observableArrayList();

    //Columnas de la tabla.
    @FXML
    private  TableColumn rankIniCol, NombreJugadorCol, eloCol, fideIDCol, infoCol;

    //Desplegable que contiene la seleccion de torneo.
    @FXML
    private ChoiceBox<String> tournChoiceBox;

    String [] choices = {"Open A","Open B"};

    //Variables recurrentes en el codigo.
    String querysql = "";

    PreparedStatement ps = null;

    Connection cnx = null;

    ResultSet rs = null;

    jugador j = null;

    //Método Inicializador.
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Inicia conexión a base de datos.
            cnx = getConnexion();

            //Añade opciones a la choice box y hace set de un valor para que no quede nula.
            tournChoiceBox.getItems().addAll(choices);

            tournChoiceBox.setValue("Open A");

            //Carga los datos.
            loadData();

            //Lista filtrada de jugadores.
            FilteredList<jugador> filteredList = new FilteredList<>(jugadores, b -> true);

            //Añade un listener al campo de texto para que filtre dinámicamente.
            filtertxt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(jugador -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (jugador.getNombreJugador().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                        return true;
                    }else {
                        return false;
                    }
                });

                SortedList<jugador> sortedList = new SortedList<>(filteredList);

                sortedList.comparatorProperty().bind(table.comparatorProperty());

                table.setItems(sortedList);
            });

            //Añade un listener a la choice box para que cargue los datos cada vez que cambie su valor.
            tournChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    loadData();
                }
            });
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Carga los datos de la base de datos y los añade a la lista de jugadores.
    public void refreshTable(){
        try {
            //Borra los objetos de la lista cada vez que se invoca el método.
            jugadores.clear();

            //Según la selección de la choice box, hace una consulta u otra.
            if (tournChoiceBox.getValue().contains("Open A")) {
                querysql = "SELECT * FROM jugador WHERE tipoTorneo like 'A'";
            } else {
                querysql = "SELECT * FROM jugador WHERE tipoTorneo like 'B'";
            }

            ps = cnx.prepareStatement(querysql);

            rs = ps.executeQuery();

            //Añade a la lista los jugadores que se han pedido en la consulta a la base de datos.
            while (rs.next()) {
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
            rs.close();
            ps.close();

            //Se añade a la tabla la lista con los jugadores.
            table.setItems(jugadores);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Carga los datos de la lista de jugadores a las celdas de la tabla.
    public void loadData(){
        refreshTable();

        this.NombreJugadorCol.setCellValueFactory(new PropertyValueFactory<jugador, String>("nombreJugador"));
        this.rankIniCol.setCellValueFactory(new PropertyValueFactory<jugador, Integer>("rankIni"));
        this.eloCol.setCellValueFactory(new PropertyValueFactory<jugador, Integer>("elo"));
        this.fideIDCol.setCellValueFactory(new PropertyValueFactory<jugador, Integer>("fideID"));
        this.infoCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<jugador, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<jugador, String> p) {
                String s = " ";
                if (p.getValue().isGen()) {
                    s = s + "Gen ";
                }
                if (p.getValue().isCv()) {
                    s = s + " CV";
                }
                if (p.getValue().isHotel()) {
                    s = s + " H";
                }
                if (p.getValue().getElo() <= 2400 && p.getValue().getTipoTorneo().contains("A")) {
                    s = s + " Sub2400";
                }
                if (p.getValue().getElo() <= 2200 && p.getValue().getTipoTorneo().contains("A")) {
                    s = s + " Sub2200";
                }
                if (p.getValue().getElo() <= 1800 && p.getValue().getTipoTorneo().contains("B")) {
                    s = s + " Sub1800";
                }
                if (p.getValue().getElo() <= 1600 && p.getValue().getTipoTorneo().contains("B")) {
                    s = s + " Sub1600";
                }
                if (p.getValue().getElo() <= 1400 && p.getValue().getTipoTorneo().contains("B")) {
                    s = s + " Sub1400";
                }
                if (!p.getValue().isGen()) {
                    s = "DESCALIFICADO";
                }
                return new SimpleStringProperty(s);
            }
        });
    }

    //Termina el proceso de la pantalla de datos de jugadores y te lleva de vuelta a la pantalla de selección.
    public void endAction(){
        try {
            //Cargamos la vista de selección.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("selectionScreen.fxml"));

            //Cargamos el padre.
            Parent selectionScene = loader.load();

            //Cargamos el controlador de la vista.
            SelectionScreen controller = loader.getController();

            //Creamos y cargamos la escena.
            Scene scene = new Scene(selectionScene);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Pantalla principal");
            stage.setScene(scene);
            stage.show();

            //Cerramos la ventana actual.
            Stage mystage = (Stage) filterLabel.getScene().getWindow();
            mystage.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //Añade jugador a la base de datos.
    public void addAction(ActionEvent event) throws IOException, SQLException {
        //Cargamos la vista secundaria.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondaryView.fxml"));

        //Cargamos el padre.
        Parent secondaryScene = loader.load();

        //Cargamos el controlador de la vista.
        SecondaryController controlador = loader.getController();

        controlador.getAttributes(jugadores);

        controlador.setCheck(true);

        //Creamos y cargamos la escena.
        Scene scene = new Scene(secondaryScene);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Introducir datos de jugador");
        stage.setScene(scene);
        stage.showAndWait();

        //Cargamos los datos introducidos a un objeto de jugador.
        j = controlador.getJugador();

        if(j != null) {
            //Preparamos la query.
            ps = cnx.prepareStatement("INSERT INTO jugador(rankIni,posicion,NombreJugador,fideID,ELO,gen,CV,hotel,tipoTorneo) VALUES (?,?,?,?,?,?,?,?,?)");

            //Cargamos los parámetros del objeto a los parámetros de la query.
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

    //Realiza acción de borrado sobre la base de datos.
    public void deleteAction(ActionEvent event)throws SQLException{
        //Creo un jugador de la selección de la tabla.
        j = this.table.getSelectionModel().getSelectedItem();

        //Si no hay jugador seleccionado, mostramos error.
        if (j == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error!");
            alert.setTitle("Delete Action Error.");
            alert.setContentText("Debes seleccionar una persona");
            alert.showAndWait();
        }else{
            //Preparamos la query.
            ps = cnx.prepareStatement("DELETE FROM jugador WHERE fideID = ?");

            //Usamos el fideID del jugador seleccionado para realizar la acción de borrado.
            ps.setInt(1,j.getFideID());
            ps.executeUpdate();
            ps.close();

            //Refrescamos la vista
            refreshTable();
        }
    }

    //Modifica los datos del jugador seleccionado.
    public void modifyAction(ActionEvent event){
        //Creo un jugador de la selección de la tabla.
        j = this.table.getSelectionModel().getSelectedItem();

        //Si no hay jugador seleccionado mostramos error.
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

                controlador.setCheck(false);

                //Creamos y mostramos la escena.
                Scene scene = new Scene(secondaryScene);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Modificacion de Datos");
                stage.setScene(scene);
                stage.showAndWait();

                //Pido los datos del jugador modificado al controlador secundario.
                jugador jModified = controlador.getJugador();

                //Se llama al método que hace el update en la base de datos.
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

    //Elige archivo csv y vuelca sus datos en la base de datos.
    public void importDataAction(ActionEvent event) throws IOException,SQLException {
        //Ventana de elegir archivo mediante explorador de windows.
        FileChooser fc = new FileChooser();

        //Nombre de la ventana.
        fc.setTitle("Elegir archivo CSV.");

        File initialFP = new File(System.getProperty("user.home") + "/Desktop");

        fc.setInitialDirectory(initialFP);

        Stage stage = new Stage();

        //Creamos un objeto de archivo, y lo definimos como el archivo elegido mediante el file chooser.
        File selectedFile = fc.showOpenDialog(stage);

        //Creamos una lista.
        ArrayList<jugador> list;

        if(selectedFile != null) {
            //Llamamos al método que lee el archivo seleccionado y lo transforma en objetos de jugador, lo cuales insertamos en la lista.
            list = datos(selectedFile);

            //Creamos un iterator de la lista que contiene los jugadores.
            Iterator<jugador> it = list.iterator();

            //Creamos la query de sql para insertar en la base de datos.
            ps = cnx.prepareStatement("INSERT INTO jugador(rankIni,posicion,NombreJugador,fideID,ELO,gen,CV,hotel,tipoTorneo) VALUES (?,?,?,?,?,?,?,?,?)");

            //Iteramos por la lista de jugadores, añadiéndolos a la base de datos.
            while (it.hasNext()) {
                jugador j = it.next();
                ps.setInt(1, j.getRankIni());
                ps.setInt(2, j.getPosicion());
                ps.setString(3, j.getNombreJugador());
                ps.setInt(4, j.getFideID());
                ps.setInt(5, j.getElo());
                ps.setBoolean(6, j.isGen());
                ps.setBoolean(7, j.isCv());
                ps.setBoolean(8, j.isHotel());
                ps.setString(9, j.getTipoTorneo());
                ps.executeUpdate();
            }
            //Cerramos el statement y cargamos los datos a la vista.
            ps.close();
            loadData();
        }
    }

    //Crea archivo con los nombres de los jugadores y los premios a los que optan.
    public void exportDataAction(ActionEvent event) throws IOException,SQLException {
        //Preparamos la query.
        if(tournChoiceBox.getValue().contains("A")) {
            ps = cnx.prepareStatement("SELECT * FROM PremiosOptadosPorOpenA");
        }else{
            ps = cnx.prepareStatement("SELECT * FROM PremiosOptadosPorOpenB");
        }

        rs = ps.executeQuery();

        //Creamos un selector de archivos para indicar el directorio donde crear el archivo exportado.
        FileChooser fc = new FileChooser();

        fc.setTitle("Selecciona donde quieres guardar el archivo exportado");

        File initialFP = new File(System.getProperty("user.home") + "/Desktop");

        fc.setInitialDirectory(initialFP);

        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        Stage stage = new Stage();

        File selectedFile = fc.showSaveDialog(stage);

        if(selectedFile != null) {
            //Le damos al escritor de archivos la direccion seleccionada con el selector de archivos.
            BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));

            bw.write("Los jugadores optan a los siguientes premios.");

            bw.newLine();

            //Mientras la query tenga resultados, seguirá escribiendo líneas en el archivo.
            while (rs.next()) {
                String nom = rs.getString(1);
                String premio = rs.getString(2);
                String line = nom + " opta a " + premio;
                bw.newLine();
                bw.write(line);
                bw.newLine();
                bw.write("--------------------------------");
            }
            rs.close();
            ps.close();
            bw.close();
        }
    }

    //TODO Buscar mejor forma de saltar los datos que no nos interesan al principio.
    //Lee datos del archivo csv.
    public ArrayList<jugador> datos(File file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = "";

        boolean cv = false;

        boolean hotel = false;

        //Queremos un contador para saltar las primeras líneas del archivo CSV.
        int contador = 0;

        ArrayList<jugador> list = new ArrayList<>();

        //Creamos un bucle, el cual itera mientras el lector tenga lineas para leer, que crea objetos de jugador con la informacion leida y los añade a una lista.
        while ((line = br.readLine()) != null) {
            if (contador > 4) {
                //Dividimos la linea leida por los puntos y coma e introducimos cada campo leido en un array.
                String[] ArrayLine = line.split(";");
                 if (ArrayLine.length == 10) {
                    if (ArrayLine[9].contains("H")) {
                        hotel = true;
                    }
                    if (ArrayLine[9].contains("CV")) {
                        cv = true;
                    }
                    if (tournChoiceBox.getValue().contains("A")) {
                        jugador j = new jugador(Integer.parseInt(ArrayLine[0]), 0, ArrayLine[2],Integer.parseInt(ArrayLine[6]), Integer.parseInt(ArrayLine[4]), true, cv, hotel, "A");
                        list.add(j);
                    } else {
                        jugador j = new jugador(Integer.parseInt(ArrayLine[0]), 0, ArrayLine[2],Integer.parseInt(ArrayLine[6]), Integer.parseInt(ArrayLine[4]), true, cv, hotel, "B");
                        list.add(j);
                    }
                }else if(ArrayLine.length < 9 && ArrayLine.length > 2){
                     if (tournChoiceBox.getValue().contains("A")) {
                         jugador j = new jugador(Integer.parseInt(ArrayLine[0]), 0, ArrayLine[2], Integer.parseInt(ArrayLine[6]), Integer.parseInt(ArrayLine[4]), true, false, false, "A");
                         list.add(j);
                     } else {
                             jugador j = new jugador(Integer.parseInt(ArrayLine[0]), 0, ArrayLine[2], Integer.parseInt(ArrayLine[6]), Integer.parseInt(ArrayLine[4]), true, false, false, "B");
                             list.add(j);
                     }
                }
                 //Reiniciamos parametros.
                 cv = false;
                 hotel = false;
            }else{
                //Aumentamos el contador para saltar la cabecera del archivo
                contador++;
            }
        }
        return list;
    }

    //Realiza un update sobre la base de datos.
    public  void update(jugador j, int fideID) throws SQLException{
        //Preparamos la query de update.
        ps = cnx.prepareStatement("UPDATE jugador SET rankIni = ?, posicion = ?, nombreJugador = ?, fideID = ?, ELO = ?, gen = ?, CV = ?, hotel = ?, tipoTorneo = ? WHERE fideID = ?");

        //Colocamos los parametros para la modificacion del jugador.
        ps.setInt(1,j.getRankIni());
        ps.setInt(2,j.getPosicion());
        ps.setString(3,j.getNombreJugador());
        ps.setInt(4,j.getFideID());
        ps.setInt(5,j.getElo());
        ps.setBoolean(6,j.isGen());
        ps.setBoolean(7,j.isCv());
        ps.setBoolean(8,j.isHotel());
        ps.setString(9,j.getTipoTorneo());

        //Colocamos el parametro que nos indica el jugador que queremos modificar.
        ps.setInt(10,fideID);

        //Ejecutamos update.
        ps.executeUpdate();
        ps.close();
    }
}