package johanneslampela5;

import java.util.Scanner;
import java.util.ArrayList;

public class main {
    public static void main(String[] args){
        double pinta_ala;
        double a_pinta_ala;
        int maara;
        int a_maara;
        int asukas;
        Tontti tontti = null;

        Scanner lukija = new Scanner(System.in);

        System.out.println("Annetaan tontin tiedot");

        System.out.println("Anna nimi: ");
        String nimi = lukija.nextLine();

        System.out.println("Anna osoite: ");
        String osoite = lukija.nextLine();
        
        do {
            System.out.println("Anna tontin pinta-ala: ");
            pinta_ala = lukija.nextDouble();
            if(pinta_ala <= 0){
                System.out.println("Anna pinta-ala, joka on suurempi kuin 0!");
            }
        } while (pinta_ala <= 0);

        do {
            System.out.println("Anna rakennuksen asuntojen lukumäärä: ");
            a_maara = lukija.nextInt();
            if(a_maara <= 0){
                System.out.println("Anna luku joka on suurempi kuin 0!");
            }
        } while (a_maara <= 0);

        while(tontti == null){
            System.out.println("Valitse 1, jos haluat lisätä omakotitalon.");
            System.out.println("Valitse 2, jos haluat lisätä rivitalon.");
            System.out.println("Valitse 3, jos haluat lisätä kerrostalon.");
            System.out.println("Valitse 4, jos et haluat valita rakennustyyppiä.");

            int valinta = lukija.nextInt();
            switch(valinta){
                case 1:
                tontti = new Tontti(nimi, osoite, pinta_ala, new Omakotitalo(a_maara));
                break;

                case 2:
                tontti = new Tontti(nimi, osoite, pinta_ala, new Rivitalo(a_maara));
                break;

                case 3:
                tontti = new Tontti(nimi, osoite, pinta_ala, new Kerrostalo(a_maara));
                break;

                case 4:
                tontti = new Tontti(nimi, osoite, pinta_ala, new Rakennus(a_maara));
                break;
            }
        }

        if(a_maara > 0){
            for(int i = 0; i<a_maara; i++){
                
                do {
                    System.out.println("Anna " + Integer.toString(i+1) + "." + " asunnon pinta-ala: ");
                    a_pinta_ala = lukija.nextDouble();
                    if(a_pinta_ala<=0){
                        System.out.println("Anna luku, joka on suurempi kuin 0!");
                    }
                } while (a_pinta_ala<=0);

                do {
                    System.out.println("Anna " + Integer.toString(i+1) + "." + " asunnon huoneiden lukumäärä: ");
                    maara = lukija.nextInt();
                    if(maara <= 0){
                        System.out.println("Anna luku, joka on suurempi kuin 0!");
                    }
                } while (maara <= 0);
                
                do {
                    System.out.println("Anna "+ Integer.toString(i+1) + "." + " asunnon asukkaiden määrä: ");
                    asukas = lukija.nextInt();
                    if(asukas<=0){
                        System.out.println("Anna luku, joka on suurempi kuin 0!");
                    }
                } while (asukas<=0);

                lukija.nextLine();

                ArrayList<Asukas> asunnon_asukkaat = new ArrayList<>();

                for(int j = 0; j<asukas; j++){
                    System.out.println("Anna " + Integer.toString(j+1) + "." + " asukkaan nimi: ");
                    String asukas_nimi = lukija.nextLine();
                    asunnon_asukkaat.add(new Asukas(asukas_nimi));
                }
                tontti.getRakennus().addAsunto(new Asunto(a_pinta_ala, maara, asunnon_asukkaat));

            }
        }

        while(true){
            System.out.println("Valitse 1, jos haluat tulostaa tiedot.");
            System.out.println("Valitse 2, jos haluat poistua ohjelmasta.");

            int valinta2 = lukija.nextInt();

            switch(valinta2){
                case 1:
                tontti.printInfo();
                break;

                case 2:
                lukija.close();
                System.exit(0);
            }   
        }
    }
}
