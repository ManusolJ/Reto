public class metodosFXML {

// hay que revisar formas de determinar la posicion para cada premio (quiero intentar no poner 50 variables xd)
    public static void premioA(int posicion,int elo,boolean cv, boolean hotel) throws ArrayIndexOutOfBoundsException{

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

        System.out.println(premioMayor);
    }




}
