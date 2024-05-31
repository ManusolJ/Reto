package com.example.reto2;

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

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class PrizeController implements Initializable {

    //TODO EXPORTAR A TXT LISTADO DE PREMIOS

    //Conexión a base de datos.
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://ajedrezalpha.clt77wc4mhcd.us-east-1.rds.amazonaws.com:3306/ajedrez";
        String user = "root";
        String password = "RootRoot1";
        return DriverManager.getConnection(url, user, password);
    }

    //Etiquetas.
    @FXML
    private Label filterLabel;

    //Choice box y sus posibles elecciones.
    @FXML
    ChoiceBox<String> prizeChoiceBox,tournChoiceBox;

    String [] prizeChoiceA = {"General","Comunidad Valenciana","Alojamiento","Sub2400","Sub2200"};

    String [] prizeChoiceB = {"General","Comunidad Valenciana","Alojamiento","Sub1800","Sub1600","Sub1400"};

    String [] tournChoice = {"Open A","Open B"};

    ObservableList<String> prizeChoiceA1 = FXCollections.observableArrayList(prizeChoiceA);

    ObservableList<String> prizeChoiceB1 = FXCollections.observableArrayList(prizeChoiceB);

    //Campo de texto.
    @FXML
    private TextField txtFilter;

    //Botones.
    @FXML
    private Button exitBtn,winnerImport,winnerExport;

    //Tablas y listas.
    @FXML
    private TableView<ganador> table;

    private ObservableList<jugador> jugadores = FXCollections.observableArrayList();

    private ObservableList<premio> premios = FXCollections.observableArrayList();

    private ObservableList<ganador> ganadores = FXCollections.observableArrayList();

    //Columnas de la tabla.
    @FXML
    private TableColumn posCol,nameCol,prizeCol;

    //Variables recurrentes.
    PreparedStatement ps;

    Connection cnx;

    String querysql = "";

    ResultSet rs;

    // Método de inicialización que se llama después de cargar el archivo FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Iniciamos conexion a base de datos.
            cnx = getConnexion();

            //Añadimos opciones a choice box y hacemos set.
            tournChoiceBox.getItems().addAll(tournChoice);

            prizeChoiceBox.setItems(prizeChoiceA1);

            tournChoiceBox.setValue("Open A");

            prizeChoiceBox.setValue("General");

            //Cargamos datos de ganadores.
            loadWinner();

            //Lista filtrada.
            FilteredList<ganador> filteredList = new FilteredList<>(ganadores, b -> true);

            //Añadimos listener a campo de texto para filtrar por nombre de jugador.
            txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(ganador -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (ganador.getNombre().toLowerCase().indexOf(lowerCaseFilter) > -1) {
                        return true;
                    }else {
                        return false;
                    }
                });

                SortedList<ganador> sortedList = new SortedList<>(filteredList);

                sortedList.comparatorProperty().bind(table.comparatorProperty());

                table.setItems(sortedList);
            });

            //Añadimos lister al selector de torneo para añadir unas opciones de premios u otras.
            tournChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if(tournChoiceBox.getValue().equals("Open A")){
                        prizeChoiceBox.setItems(prizeChoiceA1);
                        prizeChoiceBox.setValue("General");

                    }else{
                        prizeChoiceBox.setItems(prizeChoiceB1);
                        prizeChoiceBox.setValue("General");
                    }
                }
            });

            //Añadimos un listener al selector de categoria de premio para mostrar los premios de una u otra categoria.
            prizeChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    loadWinner();
                }
            });

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Carga datos de la lista a la tabla.
    public void loadWinner(){
        refreshTable();

        this.posCol.setCellValueFactory(new PropertyValueFactory<ganador,Integer>("rangoFinal"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<ganador,String>("nombre"));
        this.prizeCol.setCellValueFactory(new PropertyValueFactory<ganador,Integer>("importePremio"));
    }

    //Carga datos de la base de datos a la lista.
    public void refreshTable(){
        try {
            //Borramos los datos de la lista de ganadores cada vez que llamamos al método.
            ganadores.clear();

            //Segun la eleccion de las choice box, cargaremos diferentes consultas.
            if (tournChoiceBox.getValue().contains("Open A")) {
                if(prizeChoiceBox.getValue() != null) {
                    switch (prizeChoiceBox.getValue()) {
                        case "General" -> querysql = "SELECT * FROM Ganadores_general_A";
                        case "Comunidad Valenciana" -> querysql = "SELECT * FROM Ganadores_cv_A";
                        case "Alojamiento" -> querysql = "SELECT * FROM Ganadores_hotel_A";
                        case "Sub2400" -> querysql = "SELECT * FROM Ganadores_Sub2400";
                        case "Sub2200" -> querysql = "SELECT * FROM Ganadores_Sub2200";
                    }
                }
            } else if (tournChoiceBox.getValue().contains("Open B")) {
                if(prizeChoiceBox.getValue() != null){
                    switch (prizeChoiceBox.getValue()) {
                        case "General" -> querysql = "SELECT * FROM Ganadores_general_B";
                        case "Comunidad Valencia" -> querysql = "SELECT * FROM Ganadores_cv_B";
                        case "Alojamiento" -> querysql = "SELECT * FROM Ganadores_hotel_B";
                        case "Sub1800" -> querysql = "SELECT * FROM Ganadores_Sub1800";
                        case "Sub1600" -> querysql = "SELECT * FROM Ganadores_Sub1600";
                        case "Sub1400" -> querysql = "SELECT * FROM Ganadores_Sub1400";
                    }
                }
            }
            if (prizeChoiceBox != null) {
                ps = cnx.prepareStatement(querysql);

                rs = ps.executeQuery();

                while (rs.next()) {
                    ganadores.add(new ganador(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3)
                    ));
                }
                table.setItems(ganadores);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Importa los datos del csv a la base de datos
    public void importWinnerAction(ActionEvent event) throws SQLException,IOException {
        FileChooser fc = new FileChooser();

        Stage stage = new Stage();

        File selectedFile = fc.showOpenDialog(stage);

        if(selectedFile != null) {
            ArrayList<jugador> list = leerDatos(selectedFile);

            Iterator<jugador> it = list.iterator();

            if (tournChoiceBox.getValue().equals("Open A")) {
                ps = cnx.prepareStatement("UPDATE jugador SET posicion = ? WHERE RankIni = ? AND nombreJugador like ? AND tipoTorneo like 'A'");
            } else {
                ps = cnx.prepareStatement("UPDATE jugador SET posicion = ? WHERE RankIni = ? AND nombreJugador like ? AND tipoTorneo like 'B'");
            }

            while (it.hasNext()) {
                jugador j = it.next();
                ps.setInt(1, j.getPosicion());
                ps.setInt(2, j.getRankIni());
                ps.setString(3, j.getNombreJugador());
                ps.executeUpdate();
            }
            ps.close();

            winnerAlgorithm();

            loadWinner();
        }
    }

    //Exporta la lista de los ganadores
    public void exportWinnerAction(ActionEvent event){
        try {
            if (tournChoiceBox.getValue().contains("A")) {
                ps = cnx.prepareStatement("SELECT * FROM resultado_A");
            } else {
                ps = cnx.prepareStatement("SELECT * FROM resultado_B");
            }

            rs = ps.executeQuery();

            FileChooser fc = new FileChooser();

            fc.setTitle("Selecciona donde quieres guardar el archivo exportado");

            File initialFP = new File(System.getProperty("user.home") + "/Desktop");

            fc.setInitialDirectory(initialFP);

            fc.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );

            Stage stage = new Stage();

            File selectedFile = fc.showSaveDialog(stage);

            initialFP = selectedFile;

            if(initialFP != null) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(selectedFile));
                if(tournChoiceBox.getValue().contains("A")){
                    bw.write("Ganadores del torneo Open A.");
                }else{
                    bw.write("Ganadores del torneo Open B.");
                }

               bw.newLine();

                while (rs.next()) {
                    String categoria = rs.getString(1);
                    int puesto = rs.getInt(2);
                    String nom = rs.getString(3);
                    int importe = rs.getInt(4);
                    String separator = "";
                    String line = "| " + categoria + " | Puesto : " + puesto + " | " + nom + " | Importe : " + importe + " |";
                    for(int i = 0; i < line.length();i++){
                        separator = separator + "-";
                    }
                    System.out.println(line);
                    bw.write(separator);
                    bw.newLine();
                    bw.write(line);
                    bw.newLine();
                    bw.write(separator);
                    bw.newLine();
                }
                rs.close();
                ps.close();
                bw.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //Accion que realiza al cerrar ventana
    public void endAction() {
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

    //Algoritmo de reparto de premios.
    public void winnerAlgorithm() throws SQLException{
        jugadores.clear();

        premios.clear();

        if(tournChoiceBox.getValue().equals("Open A")){
            ps = cnx.prepareStatement("SELECT * FROM jugador WHERE tipoTorneo like 'A'");
        }else{
            ps = cnx.prepareStatement("SELECT * FROM jugador WHERE tipoTorneo like 'B'");
        }

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
                    rs.getString(9)
            ));
        }

        if (tournChoiceBox.getValue().equals("Open A")) {
            ps = cnx.prepareStatement("SELECT * FROM ganaPremio where tipotorneo like 'A'");
        }else{
            ps = cnx.prepareStatement("SELECT * FROM ganaPremio where tipotorneo like 'B'");
        }

        rs = ps.executeQuery();

        while (rs.next()){
            premios.add(new premio(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getInt(3),
                    0,
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getInt(7)
            ));
        }

        //Ordena la lista de premios, primero por su importe, despues por su prioridad y finalmente por su nombre.
        premios.sort(new Comparator<premio>() {
            @Override
            public int compare(premio o1, premio o2) {
                if(o1.getImporte() != o2.getImporte()) {
                    return o2.getImporte() - o1.getImporte();
                }else if(o1.getImporte() == o2.getImporte() && o1.getPrioridad() != o2.getPrioridad()){
                    return o1.getPrioridad() - o2.getPrioridad();
                }else {
                    return o2.getNombrePremio().compareTo(o1.getNombrePremio());
                }
            }
        });

        //Ordena la lista de jugadores segun su posicion final.
        jugadores.sort(new Comparator<jugador>() {
            @Override
            public int compare(jugador o1, jugador o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });

        // Para asegurar que cada jugador solo reciba un premio
        Set<Integer> jugadoresAsignados = new HashSet<>();

        // Asignar premios a los jugadores
        for (premio p : premios) {
            for (jugador j : jugadores) {
                if (!jugadoresAsignados.contains(j.getFideID()) && cumpleRequisitos(j, p)) {
                    p.setFideID(j.getFideID());
                    jugadoresAsignados.add(j.getFideID());
                    break;
                }
            }
        }

        ps = cnx.prepareStatement("UPDATE ganaPremio SET fideID = ? WHERE idPremio = ?");

        for (premio p : premios) {
            ps.setInt(1, p.getFideID());
            ps.setInt(2, p.getIdPremio());
            ps.executeUpdate();
        }

        ps.close();
    }

    // Método para comprobar si un jugador puede obtener un premio
    private boolean cumpleRequisitos(jugador j, premio p) {

        switch (p.getNombrePremio().toLowerCase()) {
            case "general":
                return true;
            case "comunidad valenciana":
                return j.isCv();
            case "hotel":
                return j.isHotel();
            case "sub2400":
                return j.getElo() < 2400;
            case "sub2200":
                return j.getElo() < 2200;
            case "sub1800":
                return j.getElo() < 1800;
            case "sub1600":
                return j.getElo() < 1600;
            case "sub1400":
                return j.getElo() < 1400;
            default:
                return false;
        }
    }

    //Lee los datos del csv y los carga a una lista
    private ArrayList<jugador> leerDatos(File file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = "";

        int contador = 0;

        ArrayList<jugador> list = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] arrayLine = line.split(";");
            if(contador > 4 && arrayLine.length > 1) {
                jugador j = new jugador(Integer.parseInt(arrayLine[0]), Integer.parseInt(arrayLine[1]),arrayLine[4]);
                list.add(j);
            }
            contador++;
        }
        return list;
    }
}