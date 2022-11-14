package johanneslampela1;

import java.util.Scanner;

public class PankkitiliMain{
    public static void main(String[] args){
        Scanner lukija = new Scanner(System.in);
        String tilinumero;
        String omistaja;
        double saldo;
    
        System.out.println("Tehdään pankkitili.");
        System.out.println("Anna tilinumerosi: ");
        tilinumero = lukija.nextLine();

        System.out.println("Anna nimesi: ");
        omistaja = lukija.nextLine();

        System.out.println("Anna saldo: ");
        saldo = lukija.nextDouble();

        Pankkitili tili = new Pankkitili(tilinumero, omistaja, saldo);
        
        /*
        Voitaisiin myös tehdä settereillä ja gettereillä

        Pankkitili tili2 = new Pankkitili();
        tilinumero = lukija.nextLine();
        tili2.setTilinumero(tilinumero);
        omistaja = lukija.nextLine();
        tili2.setOmistaja(omistaja);
        saldo = lukija.nextDouble();
        tili2.setSaldo(saldo);
        */

        while(true){
            System.out.println("Valitse 1, jos haluat nostaa rahaa.");
            System.out.println("Valitse 2, jos haluat tallettaa rahaa.");
            System.out.println("Valitse 3, jos haluat tulostaa tilitiedot.");
            System.out.println("Valitse 4, jos haluat poistua ohjelmasta.");

            int valinta = lukija.nextInt();
            switch(valinta){
                case 1:
                System.out.println("Nostettava summa: ");
                double summa = lukija.nextDouble();
                tili.otto(summa);
                break;

                case 2:
                System.out.println("Talletettava summa: ");
                double maara = lukija.nextDouble();
                tili.talletus(maara);
                break;

                case 3:
                System.out.println(tili.toString());
                //System.out.println("Tilinumero: " + tili.getTilinumero() + "\nOmistaja: " + tili.getOmistaja() + "\nSaldo: " + tili.getSaldo() + " euroa"); //Easy with getters too
                break;

                case 4:
                lukija.close();
                System.exit(0);
            }   
        }
    }
}