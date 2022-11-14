package johanneslampela6;

import java.io.File;
import java.util.*;

public class Sanalista {

    private List<String> sanalista = new ArrayList<>();

    public Sanalista(List<String> sanalista){
        this.sanalista = sanalista;
    }

    public Sanalista(String path){
        try {
            Scanner sc = new Scanner(new File(path));
            while(sc.hasNextLine()){
                String tmp = sc.nextLine().toLowerCase();
                sanalista.add(tmp);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSanalista(List<String> sanalista){
        this.sanalista = sanalista;
    }

    public List<String> annaSanat(){
        return sanalista;
    }

    //Metodi palauttaa uuden sanalista-olion, jossa on vain ne sanat, joiden pituus on
    //parametrina annetun muuttujan arvon mukaisia.
    public Sanalista sanatJoidenPituusOn(int pituus){
        List<String> sana_lista = new ArrayList<>();
        for(String x : sanalista){
            if(x.length() == pituus){
                sana_lista.add(x.toLowerCase());
            }
        }
        return new Sanalista(sana_lista);
    }
    //Metodi palauttaa uuden sanalista-olion, jossa on vain ne sanat, joissa on merkit
    //parametrina annetun merkkijonon määrämissä kohdissa. Annettu merkkijono on muotoa
    //_a_e__ (esimerkiksi kameli, kaveri) missä alaviivat kuvaavat mitä tahansa merkkiä ja
    //kirjaimet merkkejä, joiden täytyy olla sanassa juuri annetulla paikalla.
    public Sanalista sanatJoissaMerkit(String merkki){
        List<String> sana_lista = new ArrayList<>();
        merkki = merkki.toLowerCase();
        for(String x : sanalista){
            Boolean tmp = true;
            if(x.length() == merkki.length()){
                for(int i = 0; i<x.length(); i++){
                    if(merkki.charAt(i) == '_' || merkki.charAt(i) == x.charAt(i)){
                        continue;
                    } else if(merkki.charAt(i) != x.charAt(i)){
                        tmp = false;
                    }
                }
                if(tmp){
                sana_lista.add(x);
                }
            }
        } 
        return new Sanalista(sana_lista);
    }
}
