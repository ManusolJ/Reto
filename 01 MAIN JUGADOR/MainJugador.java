import java.io.*;
import java.sql.*;


public class MainJugador {


    static Connection cnx;
    static {
        try{
            cnx = getConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static Connection getConnection () throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/ajedrez";
        String user = "root";
        String password = "root";

        return DriverManager.getConnection(url,user,password);
    }


    public static void main(String[] args) {

    }



public static void addJugadorA () throws SQLException , IOException {


        File inicioA = new File("/home/ALU1J/Descargas/Datos OpenA.txt");  //fideID
        File finalA = new File("/home/ALU1J/Descargas/Clasificacion Open A.txt"); // el resto

    Array[] listaInicioA = {};

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));





  //  PreparedStatement ps = cnx.prepareStatement("INSERT INTO ajedrez (posicion, ranki, nombre, fideID, fide, cv, hotel, tipo) VALUES (?,?,?,?,?,?,?,?)");
    PreparedStatement ps = cnx.prepareStatement("INSERT INTO ajedrez (posicion, ranki, nombre, fideID, fide, cv, hotel ) VALUES (?,?,?,?,?,?,?)");









}



}
