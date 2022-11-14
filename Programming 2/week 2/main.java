package johanneslampela2;

import java.util.Scanner;
import java.util.List;

public class main {
    public static void main(String[] args){
        double pinta_ala;
        int maara;

        Scanner lukija = new Scanner(System.in);

        System.out.println("Annetaan tontin tiedot");

        System.out.println("Anna nimi: ");
        String nimi = lukija.nextLine();

        System.out.println("Anna leveyspiiri: ");
        String leveyspiiri = lukija.nextLine();

        System.out.println("Anna pituuspiiri: ");
        String pituuspiiri = lukija.nextLine();
        
        do {
            System.out.println("Anna pinta-ala: ");
            pinta_ala = lukija.nextDouble();
            if(pinta_ala <= 0){
                System.out.println("Anna pinta-ala, joka on suurempi kuin 0!");
            }
        } while (pinta_ala <= 0);
        
        Tontti tontti = new Tontti(nimi, leveyspiiri, pituuspiiri, pinta_ala);

        do {
            System.out.println("Anna rakennuksen pinta-ala: "); 
            pinta_ala = lukija.nextDouble();
            if(pinta_ala <= 0){
                System.out.println("Anna pinta-ala, joka on suurempi kuin 0!");
            }
        } while (pinta_ala <=0);

        do {
            System.out.println("Anna rakennuksen huoneiden lukumäärä: ");
            maara = lukija.nextInt();
            if(maara <= 0){
                System.out.println("Anna luku joka on suurempi kuin 0!");
            }
        } while (maara <= 0);

        tontti.setRakennus(pinta_ala, maara);

        while(true){
            System.out.println("Valitse 1, jos haluat lisätä asukkaan.");
            System.out.println("Valitse 2, jos haluat tulostaa tontin tiedot.");
            System.out.println("Valitse 3, jos haluat tulostaa rakennuksen tiedot.");
            System.out.println("Valitse 4, jos haluat tulostaa asukkaat.");
            System.out.println("Valitse 5, jos haluat poistua ohjelmasta.");

            int valinta = lukija.nextInt();
            switch(valinta){
                case 1:
                lukija.nextLine();
                System.out.println("Anna asukkaan nimi: ");
                String asukasNimi = lukija.nextLine();

                System.out.println("Anna asukkaan syntymäaika: ");
                String asukasAika = lukija.nextLine();
                tontti.getRakennus().addAsukas(asukasNimi, asukasAika);
                break;

                case 2:
                System.out.println("***************");
                System.out.println(tontti.toString());
                System.out.println("***************");
                break;

                case 3:
                System.out.println("***************");
                System.out.println(tontti.getRakennus().toString());
                System.out.println("***************");
                break;

                case 4:
                if(tontti.getRakennus().getAsukkaat().isEmpty()){
                    System.out.println("***************");
                    System.out.println("Ei asukkaita lisätty.");
                    System.out.println("***************");
                } else {
                    List<Asukas> a = tontti.getRakennus().getAsukkaat();
                    System.out.println("***************");
                    for(Asukas x : a){
                        System.out.println(x.toString());
                        System.out.println("***************");
                    }
                }
                break;

                case 5:
                lukija.close();
                System.exit(0);
            }   
        }
    }
}
