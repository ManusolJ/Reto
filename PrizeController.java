package com.example.proyector2;

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
import java.sql.*;
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

    public class metodosFXML {

        private static Connection getConnexion() throws SQLException {
            String url = "jdbc:mysql://localhost:3306/ajedrezR2";
            String user = "root";
            String password = "root";
            return DriverManager.getConnection(url, user, password);
        }

// hay que revisar formas de determinar la posicion para cada premio (quiero intentar no poner 50 variables xd)


        public static void premioA(int posicion,int elo,int fideID,boolean cv, boolean hotel) throws ArrayIndexOutOfBoundsException, SQLException {

// este select es el de la base de datos, funciona en base a fideID


            String query = "SELECT nombreJugador,fideID,j.ELO,nombrePremio from premio p join jugador j on j.tipoTorneo = p.tipoTorneo where (p.gen = true or p.cv = true or p.hotel=true or p.ELO > j.ELO) AND  fideID = "+fideID+";";

            PreparedStatement ps = getConnexion().prepareStatement(query);

            try (ResultSet rs =  ps.executeQuery()) {
                while (rs.next()) {

                    System.out.println("nombreJugador: "+rs.getString("nombreJugador"));
                    System.out.println("fideID: "+rs.getInt("fideID"));
                    System.out.println("nombrePremio: "+rs.getString("nombrePremio"));



                }
            }



            int[] valorPremioGeneral={2300,1200,650,550,500,400,300,250,200,150,100,100};
            int[] valorPremioSub2400={255,150,120,100};
            int[] valorPremioSub2200={150,100};
            int[] valorPremioCV={500,400,300,200,100};
            int valorPremioHotel=125; //20 ganadores




            int premioMayor=0;


            if (!cv){

            }

            else if (!hotel){

            }




            else if (!cv && !hotel){ // No hotel No CV

                if (elo<2200){

                    if (valorPremioGeneral[posicion]>valorPremioSub2400[posicion] && valorPremioGeneral[posicion]>valorPremioSub2200[posicion]){  //general mayor dinero
                        premioMayor=valorPremioGeneral[posicion];
                    }
                    else if (valorPremioSub2400[posicion]>valorPremioGeneral[posicion] && valorPremioSub2400[posicion]>valorPremioSub2200[posicion]) { // sub2400 mayor dinero
                        premioMayor=valorPremioSub2400[posicion];
                    }
                    else if (valorPremioSub2200[posicion]>valorPremioGeneral[posicion] && valorPremioSub2200[posicion]>valorPremioSub2400[posicion]){ // sub2200 mayor dinero
                        premioMayor=valorPremioSub2200[posicion];
                    }
                    else if (valorPremioGeneral[posicion]==valorPremioSub2400[posicion] || valorPremioGeneral[posicion]==valorPremioSub2200[posicion] ) { //si igual dinero gana general
                        premioMayor=valorPremioGeneral[posicion];
                    }
                    else if ((valorPremioGeneral[posicion]<valorPremioSub2400[posicion] || valorPremioGeneral[posicion]<valorPremioSub2200[posicion] ) && valorPremioSub2400[posicion] == valorPremioSub2200[posicion]){  // general < sub2400, general < sub2200 y sub2400=sub2200
                        premioMayor=valorPremioSub2400[posicion];
                    }
                }
                else if (elo<2400 && elo>2200) {

                    if (valorPremioGeneral[posicion]>valorPremioSub2400[posicion]){ // general > a sub2400
                        premioMayor=valorPremioGeneral[posicion];
                    }
                    else if (valorPremioGeneral[posicion]==valorPremioSub2400[posicion]){ //si general = sub2400 (se lleva general)
                        premioMayor=valorPremioGeneral[posicion];
                    }
                    else { // sub2400 > premio general
                        premioMayor=valorPremioSub2400[posicion];
                    }
                }
                else { // elo > 2400
                    premioMayor=valorPremioGeneral[posicion];
                }
            }
            else { //todos los premios
            }
            // System.out.println(premioMayor);
        }
    }
}
