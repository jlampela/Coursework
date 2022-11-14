package johanneslampela4;

import java.util.Scanner;

public class Vt4main {
    public static void main(String[] args){

        Scanner lukija = new Scanner(System.in);
        InsInfoContainer prop = new InsInfoContainer();

        while(true){
            System.out.println("Valitse 1, jos haluat lisätä vakuutustiedon.");
            System.out.println("Valitse 2, jos haluat tulostaa vakuutustiedot.");
            System.out.println("Valitse 3, jos haluat tulostaa vakuutustiedot jossa vakuutusarvo on pienempi.");
            System.out.println("Valitse 4, jos haluat tulostaa vakuutustiedot jossa vakuutusarvo on suurempi.");
            System.out.println("Valitse 5, jos haluat poistua ohjelmasta.");

            int valinta = lukija.nextInt();

            switch(valinta){

                case 1:
                lukija.nextLine();
                
                System.out.printf("Anna kiinteistön tyyppi: ");
                String tyyppi = lukija.nextLine();

                System.out.printf("Anna kiinteistö sijainti: ");
                String sijainti = lukija.nextLine();

                double arvo;
                do {
                    System.out.printf("Anna vakuutusarvo: ");
                    arvo = lukija.nextDouble();
                    if(arvo <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                    
                } while (arvo <= 0);
                
                prop.lisaaVakuutus(new InsuranceInfo(new Property(tyyppi, sijainti), arvo));
                break;

                case 2:
                prop.tulostaKaikki();
                break;

                case 3:
                double pienempi;
                do {
                    System.out.printf("Anna arvo: ");
                    pienempi = lukija.nextDouble();
                    if(pienempi <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                    
                } while (pienempi <= 0);
                prop.tulostaPienempi(pienempi);
                break;

                case 4:
                double suurempi;
                do {
                    System.out.printf("Anna arvo: ");
                    suurempi = lukija.nextDouble();
                    if(suurempi <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                    
                } while (suurempi <= 0);
                prop.tulostaSuurempi(suurempi);
                break;

                case 5:
                lukija.close();
                System.exit(0);
            }   
        }
    }
}
