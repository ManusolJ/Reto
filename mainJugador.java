import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class mainJugador {



    public static void main(String[] args) {

        //   datosInicioA();
        //  datosInicioB();
        //  datosFinA();
         datosFinB();


    }

 public static void datosInicioA () {

     try (var in = new BufferedReader(new FileReader("/home/ALU1J/Documentos/DatosOpenA.txt"))) {
         String cadena = in.readLine();
         while (cadena != null) {

             cadena = in.readLine();
             String[] partes=cadena.split(";");


            // System.out.println("posInic :"+ partes[0]);
            // System.out.println("FIdeId :"+partes[6]);

         }
     } catch (FileNotFoundException e) {
         System.out.println("ERROR. Archivo no encontrado");
     } catch (IOException e) {
         System.out.println(e.getMessage());
     } catch (NullPointerException e) {
         System.out.println("");
     }
 }

    public static void datosInicioB () {

        try (var in = new BufferedReader(new FileReader("/home/ALU1J/Documentos/DatosOpenB.txt"))) {
            String cadena = in.readLine();
            while (cadena != null) {

                cadena = in.readLine();
                String[] partes=cadena.split(";");
                System.out.println("posInic :"+ partes[0]);
                System.out.println("FIdeId :"+partes[6]);

            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. Archivo no encontrado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

    public static void datosFinA () {

        try (var in = new BufferedReader(new FileReader("/home/ALU1J/Documentos/ClasificacionOpenA.txt"))) {
            String cadena = in.readLine();


                while (cadena != null) {
                    try {
                    cadena = in.readLine();
                    String[] partes = cadena.split(";");
                    System.out.println("posFin :" + partes[0]);
                    System.out.println("Nombre :" + partes[4]);
                    System.out.println("FIde :" + partes[13]);
                    System.out.println("info :" + partes[16]);

                    String opcion = partes[16];

                    switch (opcion) {

                        case " ":
                            System.out.println("Vacio");
                            break;

                        case "H":
                            System.out.println("Hotel");
                            break;

                        case "CV":
                            System.out.println("CV");
                            break;

                        case "CVH":
                            System.out.println("Todo");
                            break;
                    }
                }catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("vacio");
                    }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. Archivo no encontrado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

    public static void datosFinB () {

        try (var in = new BufferedReader(new FileReader("/home/ALU1J/Documentos/ClasificacionOpenB.txt"))) {
            String cadena = in.readLine();


            while (cadena != null) {
                try {
                    cadena = in.readLine();
                    String[] partes = cadena.split(";");
                    System.out.println("posFin :" + partes[0]);
                    System.out.println("Nombre :" + partes[4]);
                    System.out.println("FIde :" + partes[13]);
                    System.out.println("info :" + partes[16]);

                    String opcion = partes[16];

                    switch (opcion) {

                        case " ":
                            System.out.println("Vacio");
                            break;

                        case "H":
                            System.out.println("Hotel");
                            break;

                        case "CV":
                            System.out.println("CV");
                            break;

                        case "CVH":
                            System.out.println("Todo");
                            break;
                    }
                }catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("vacio");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. Archivo no encontrado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("");
        }
    }

 }





