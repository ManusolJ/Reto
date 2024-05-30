package com.example.reto2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class PrizeController implements Initializable {

    //TODO hay que hacer todas las funciones de esta parte y quizas pensar un poco mejor el diseño de la pantalla de premios
    //TODO HACER QUERY PARA VER QUE JUGADOR GANA QUE PREMIO
    //TODO HACER QUE CADA JUGADOR SOLO PUEDA GANAR UN PREMIO
    //TODO EXPORTAR A TXT LISTADO DE PREMIOS

    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://ajedrezalpha.clt77wc4mhcd.us-east-1.rds.amazonaws.com:3306/ajedrez";
        String user = "root";
        String password = "RootRoot1";
        return DriverManager.getConnection(url, user, password);
    }

    @FXML
    private Label filterLabel;

    @FXML
    ChoiceBox<String> prizeChoiceBox,tournChoiceBox;

    String [] prizeChoiceA = {"General","Comunidad Valencia","Alojamiento","Sub2400","Sub2200"};

    String [] prizeChoiceB = {"General","Comunidad Valencia","Alojamiento","Sub1800","Sub1600","Sub1400"};

    String [] tournChoice = {"A","B"};

    ObservableList<String> prizeChoiceA1 = FXCollections.observableArrayList(prizeChoiceA);

    ObservableList<String> prizeChoiceB1 = FXCollections.observableArrayList(prizeChoiceB);

    @FXML
    private TextField txtFilter;

    @FXML
    private Button exitBtn,winnerImport;

    @FXML
    private TableView<jugador> table;

    private ObservableList<jugador> jugadores = FXCollections.observableArrayList();

    @FXML
    private TableColumn<jugador,String> posCol,nameCol,prizeCol;

    PreparedStatement ps;

    Connection cnx;

    String querysql = "";

    ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cnx = getConnexion();
        }catch (SQLException e){
            e.printStackTrace();
        }

        tournChoiceBox.getItems().addAll(tournChoice);

        prizeChoiceBox.setItems(prizeChoiceA1);

        tournChoiceBox.setValue("A");

        prizeChoiceBox.setValue("General");

        try{
            winnerAlgorithm();
        }catch (SQLException e){
            System.out.println("FUCK");
        }


        tournChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(tournChoiceBox.getValue().equals("A")){
                    prizeChoiceBox.setItems(prizeChoiceA1);
                    prizeChoiceBox.setValue("General");
                }else{
                    prizeChoiceBox.setItems(prizeChoiceB1);
                    prizeChoiceBox.setValue("General");
                }
            }
        });
    }

    public ArrayList<jugador> getJugadores(String query) throws SQLException{
        ArrayList<jugador> jugadoresList = new ArrayList<>();

        ps = cnx.prepareStatement(query);

        rs = ps.executeQuery();

        while(rs.next()){
            jugadoresList.add(new jugador(
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
        return jugadoresList;
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

    public void importWinnerAction(ActionEvent event) throws SQLException,IOException {
        FileChooser fc = new FileChooser();

        Stage stage = new Stage();

        ArrayList<jugador> jugadores;

        if(tournChoiceBox.getValue().equals("A")){
            jugadores = getJugadores("SELECT * FROM jugador WHERE tipoTorneo like 'A'");
        }else{
            jugadores = getJugadores("SELECT * FROM jugador WHERE tipoTorneo like 'B'");
        }

        File selectedFile = fc.showOpenDialog(stage);

        ArrayList<jugador> list = leerDatos(selectedFile);

        Iterator<jugador> it = list.iterator();

        ps = cnx.prepareStatement("UPDATE jugador SET posicion = ? WHERE RankIni = ? AND nombreJugador like ?");

        while(it.hasNext()){
            jugador j = it.next();
            ps.setInt(1,j.getPosicion());
            ps.setInt(2,j.getRankIni());
            ps.setString(3,j.getNombreJugador());
            ps.executeUpdate();
        }
        ps.close();
        System.out.println("DONE");
    }

    public ArrayList<jugador> leerDatos(File file) throws IOException{
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

    public void loadWinner(ActionEvent event) throws SQLException{

    }

    public void winnerAlgorithm() throws SQLException{
        ArrayList<jugador> jugadores = new ArrayList<>();

        ArrayList<premio> premios = new ArrayList<>();

        if(tournChoiceBox.getValue().equals("A")){
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

        if (tournChoiceBox.getValue().equals("A")) {
            ps = cnx.prepareStatement("SELECT * FROM ganaPremio where tipotorneo like 'A'");
        }else{
            ps = cnx.prepareStatement("SELECT * FROM ganaPremio where tipotorneo like 'B'");
        }

        rs = ps.executeQuery();

        while (rs.next()){
            premios.add(new premio(
                    rs.getString(1),
                    rs.getString(4),
                    rs.getInt(2),
                    rs.getInt(3),
                    0
            ));
        }

        premios.sort(new Comparator<premio>() {
            @Override
            public int compare(premio o1, premio o2) {
                return o1.getCategoria().compareTo(o1.getCategoria())
            }
        });

        Iterator<jugador> itj = jugadores.iterator();

        Iterator<premio> itp = premios.iterator();

        for(int i = 0;i < premios.size();i++){
            System.out.println(premios.get(i).toString());
        }

        ps = cnx.prepareStatement("UPDATE ganaPremio set ");
    }
}