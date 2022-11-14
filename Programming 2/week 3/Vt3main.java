package johanneslampela3;

import java.util.ArrayList;
import java.util.Scanner;

public class Vt3main {

    public static void main(String[] args){
        ArrayList<Subscription> tilaukset = new ArrayList<>();
        Scanner lukija = new Scanner(System.in);

        while(true){
            System.out.println("Valitse 1, jos haluat lisätä normaalitilauksen.");
            System.out.println("Valitse 2, jos haluat lisätä kestotilauksen.");
            System.out.println("Valitse 3, jos haluat tulostaa tilaukset.");
            System.out.println("Valitse 4, jos haluat poistua ohjelmasta.");

            int valinta = lukija.nextInt();

            switch(valinta){

                case 1:
                lukija.nextLine();
                
                System.out.printf("Anna lehden nimi: ");
                String lehti = lukija.nextLine();

                System.out.printf("Anna tilaajan nimi: ");
                String tilaaja = lukija.nextLine();

                System.out.printf("Anna toimitusosoite: ");
                String osoite = lukija.nextLine();

                double hinta;
                do {
                    System.out.printf("Anna kuukausihinta: ");
                    hinta = lukija.nextDouble();
                    if(hinta <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                    
                } while (hinta <= 0);

                int kesto;
                do {
                    System.out.printf("Anna tilauksen kesto: ");
                    kesto = lukija.nextInt();
                    if(kesto <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                } while (kesto <= 0);
                
                RegularSubscription tmp = new RegularSubscription(lehti, tilaaja, osoite, hinta, kesto);
                tilaukset.add(tmp);
                break;

                case 2:
                lukija.nextLine();
                
                System.out.printf("Anna lehden nimi: ");
                String lehtinimi = lukija.nextLine();

                System.out.printf("Anna tilaajan nimi: ");
                String tilaajanimi = lukija.nextLine();

                System.out.printf("Anna toimitusosoite: ");
                String tosoite = lukija.nextLine();

                double kkhinta;
                do {
                    System.out.printf("Anna kuukausihinta: ");
                    kkhinta = lukija.nextDouble();
                    if(kkhinta <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                    
                } while (kkhinta <= 0);

                int ale;
                do {
                    System.out.printf("Anna alennusprosentti: ");
                    ale = lukija.nextInt();
                    if(ale <= 0){
                        System.out.println("Anna luku suurempi kuin 0.");
                    }
                } while (ale <= 0);

                StandingSubscription tmprr = new StandingSubscription(lehtinimi, tilaajanimi, tosoite, kkhinta, ale);
                tilaukset.add(tmprr);
                break;

                case 3:
                if(tilaukset.isEmpty()){
                    System.out.println("Ei lisättyjä tilauksia.");
                } else {
                    System.out.println("***************");
                    for(Subscription x : tilaukset){
                        printSubscriptionInvoice(x);
                        System.out.println("***************");
                    }  
                }
                break;

                case 4:
                lukija.close();
                System.exit(0);
            }   
        }
        
    }

    static void printSubscriptionInvoice(Subscription subs){
        subs.printInvoice();
    }
    
}
