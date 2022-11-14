package johanneslampela6;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Hirsipuu {

    private List<Character> arvaukset = new ArrayList<>();
    private Sanalista sanalista;
    private List<String> sana_lista = new ArrayList<>();
    private int arvaustenMaara;
    private String sana;
    private String peitettysana = "";

    //Konstruktori normaalia peliä varten
    public Hirsipuu(Sanalista sanalista, int arvaustenMaara){
        this.sanalista = sanalista;
        this.arvaustenMaara = arvaustenMaara;
        int rnd = ThreadLocalRandom.current().nextInt(0,sanalista.annaSanat().size());
        this.sana = sanalista.annaSanat().get(rnd);
        for(int i = 0; i<sana.length(); i++){
            peitettysana += "_";
        }
    }
    //Konstruktori erikoispeleille
    public Hirsipuu(List<String> sana_lista, int arvaustenMaara){
        this.sana_lista = sana_lista;
        this.arvaustenMaara = arvaustenMaara;
        int rnd = ThreadLocalRandom.current().nextInt(0,sana_lista.size());
        this.sana = sana_lista.get(rnd);
        for(int i = 0; i<sana.length(); i++){
            peitettysana += "_";
        }
    }

    //Metodi vertaa parametrina annettua merkkiä arvattavaan sanaan ja lisää arvauksen
    //arvauslistalle. Jos merkki löytyy arvattavasta sanasta, palautetaan arvo true. Jos merkkiä
    //ei löydy, vähennetään arvausten määrää yhdellä ja palautetaan false.
    public boolean arvaa(Character merkki){
        if(sana.contains(merkki.toString()) && !arvaukset.contains(merkki)){
            for(int i = 0; i<sana.length(); i++){
                if(sana.charAt(i) == merkki){
                    StringBuilder sb = new StringBuilder(peitettysana);
                    sb.setCharAt(i, merkki);
                    peitettysana = sb.toString();
                }
            }
            arvaukset.add(merkki);
            return true;
        } else {
            arvaukset.add(merkki);
            arvaustenMaara--;
            return false;
        }
    }
    //Metodi palauttaa tehdyt arvaukset lista-oliona.
    public List<Character> arvaukset(){
        return arvaukset;
    }
    //Metodi palauttaa jäljellä olevien arvausten määrän.
    public int arvauksiaOnJaljella(){
        return arvaustenMaara;
    }

    //Metodi palauttaa arvattavan sanan peittelemättömänä.
    public String sana(){
        return sana;
    }

    //Metodi ilmaisee, onko peli loppu vai ei. Peli loppuu, jos arvattavan sanan kaikki merkit on arvattu.
    public boolean onLoppu(){
        if(arvauksiaOnJaljella() < 1 || sana.equals(peitettysana)){
            return true;
        }
        return false;
    }

    //Metodi palauttaa pelissä käytettävän peitetyn sanan.
    public String getPeitetty(){
        return this.peitettysana;
    }

    public void setPeitetty(String peitetty){
        this.peitettysana = peitetty;
    }
}
