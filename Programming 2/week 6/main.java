package johanneslampela6;

import java.util.*;

public class main {
    public static void main(String[] args){
        Scanner lukija = new Scanner(System.in);

        System.out.println("Anna tekstitiedoston nimi: ");
        String nimi = "johanneslampela6/" + lukija.nextLine();

        Sanalista a = new Sanalista(nimi);
        
        System.out.println("Tervetuloa pelaamaan Hirsipuuta!");
        while(true){
            System.out.println("Valitse 1, jos haluat pelata normaalin pelin");
            System.out.println("Valitse 2, jos haluat pelata pelin, jossa on vain tietyn pituiset sanat");
            System.out.println("Valitse 3, jos haluat pelata pelin, jossa on merkit parametrina annetun merkkijonon määrämissä kohdissa.");
            System.out.println("Valitse 4, jos haluat lopettaa pelin");

            int valinta = lukija.nextInt();
            //lukija.nextLine();
            
            switch(valinta){
                
                case 1:
                    game_loop(a, 1);
                    break;
                case 2:
                    game_loop(a, 2);
                    break;
                case 3:
                    game_loop(a, 3);
                    break;
                case 4:
                    lukija.close();
                    System.exit(0);
            }
        }
    }

    public static void game_loop(Sanalista a, int b){
        Scanner lukija = new Scanner(System.in);
        System.out.println("Syötä pelin arvausten määrä: ");
        int maara = lukija.nextInt();
        lukija.nextLine();
        Hirsipuu tmp = new Hirsipuu(a, maara);
        int pituus;

        if(b == 1){
            tmp = new Hirsipuu(a, maara); 
        }else if(b == 2){
            System.out.println("Anna haluamasi sanojen pituus: ");
            do {
                pituus = lukija.nextInt();
                if(pituus < 1){
                    System.out.println("Anna suurempi luku kuin 0!");
                }
            } while (pituus < 1);
            tmp = new Hirsipuu(a.sanatJoidenPituusOn(pituus).annaSanat(), maara);
        }else if(b == 3){
            System.out.println("Anna merkkijono: ");
            String merkki = lukija.nextLine();
            tmp = new Hirsipuu(a.sanatJoissaMerkit(merkki).annaSanat(), maara);
            tmp.setPeitetty(merkki);
        }
        
        //System.out.println(tmp.sana());
        while(tmp.onLoppu() == false){
            System.out.println("Arvattava sana: " + tmp.getPeitetty());
            System.out.println("Arvauksia jäljellä: " + tmp.arvauksiaOnJaljella());
            System.out.println("Arvatut kirjaimet: " + tmp.arvaukset());
            char arvaus;
            do {
                arvaus = lukija.next().charAt(0);
                if(tmp.arvaukset().contains(arvaus)){
                    System.out.println("Anna kirjain, mitä et ole jo antanut!");
                }
            } while(tmp.arvaukset().contains(arvaus));

            if(tmp.arvaa(arvaus) == true){
                System.out.println("Oikein arvattu!");
            } else {
                System.out.println("Väärin arvattu!");
            }
        }
        if(tmp.sana().equals(tmp.getPeitetty())){
            System.out.println("***************");
            System.out.println("Arvasit sanan oikein!");
            System.out.println("Sana oli: " + tmp.sana());
            System.out.println("***************");
        } else {
            System.out.println("***************");
            System.out.println("Et arvannut sanaa oikein!");
            System.out.println("Sana oli: " + tmp.sana());
            System.out.println("***************");
        }
    }
}
