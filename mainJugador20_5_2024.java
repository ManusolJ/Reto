import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class mainJugador {



    public static void main(String[] args) {

         datosInicioA();
        //  datosInicioB();
         // datosFinA();
         // datosFinB();


    }
// Hay que unir los metodos datosInicioA y datosFinA, despues lo mismo para los del B
 public static void datosInicioA () {


     try (
         var inI = new BufferedReader(new FileReader("/home/ALU1J/Documentos/DatosOpenA.txt"))) {
         String cadenaI = inI.readLine();

         /*  intento de unir metodo inicioA con finalA
         var inF = new BufferedReader(new FileReader("/home/ALU1J/Documentos/ClasificacionOpenA.txt"));
         String cadenaF = inF.readLine();
         */
         while (cadenaI != null) {
             cadenaI = inI.readLine();
             String[] partesI=cadenaI.split(";");


             System.out.println("------");
             System.out.println("posInic :"+ partesI[0]);
             System.out.println("FIdeId :"+partesI[6]);






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

        try (var inF = new BufferedReader(new FileReader("/home/ALU1J/Documentos/ClasificacionOpenA.txt"))) {
            String cadenaF = inF.readLine();


            while (cadenaF != null) {
                try {
                    cadenaF = inF.readLine();
                    String[] partesF = cadenaF.split(";");
                    System.out.println("------");
                    System.out.println("posFin :" + partesF[0]);
                    System.out.println("Nombre :" + partesF[4]);
                    System.out.println("FIde :" + partesF[13]);
                    System.out.println("info :" + partesF[16]);


                }catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("info : ");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. Archivo no encontrado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error");
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

    public static void datosFinB () {

        try (var in = new BufferedReader(new FileReader("/home/ALU1J/Documentos/ClasificacionOpenB.txt"))) {
            String cadena = in.readLine();


            while (cadena != null) {
                try {
                    cadena = in.readLine();
                    String[] partes = cadena.split(";");
                    System.out.println("------");
                    System.out.println("posFin :" + partes[0]);
                    System.out.println("Nombre :" + partes[4]);
                    System.out.println("FIde :" + partes[14]);
                    System.out.println("info :" + partes[17]);

                    String opcion = partes[16];

                    switch (opcion) {

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
                    System.out.println("info : ");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR. Archivo no encontrado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error");
        }
    }

 }
