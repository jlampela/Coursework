package johanneslampela5;

import java.util.ArrayList;

public class Asunto {

    private double pinta_ala;
    private int huoneiden_lkm;
    private ArrayList<Asukas> asukkaat;

    public Asunto(double pinta_ala, int huoneiden_lkm, ArrayList<Asukas> asukkaat){
        this.pinta_ala = pinta_ala;
        this.huoneiden_lkm = huoneiden_lkm;
        this.asukkaat = asukkaat;
    }

    public double getPinta_ala() {
        return this.pinta_ala;
    }

    public void setPinta_ala(double pinta_ala) {
        this.pinta_ala = pinta_ala;
    }

    public int getHuoneiden_lkm() {
        return this.huoneiden_lkm;
    }

    public void setHuoneiden_lkm(int huoneiden_lkm) {
        this.huoneiden_lkm = huoneiden_lkm;
    }

    public ArrayList<Asukas> getAsukkaat() {
        return this.asukkaat;
    }

    public void setAsukkaat(ArrayList<Asukas> asukkaat) {
        this.asukkaat = asukkaat;
    }

    public void addAsukas(String nimi){
        this.asukkaat.add(new Asukas(nimi));
    }

    public void printInfo(){
        System.out.println("Asunnon pinta-ala: " + this.pinta_ala + " m^2" + "\nAsunnon huoneiden lukumäärä: " + this.huoneiden_lkm + " kpl" + "\nAsunnossa asukkaita: " + asukkaat.size() + " kpl");
        if(asukkaat.isEmpty()){
            System.out.println("Ei lisättyjä asukkaita");
        } else {
            for(Asukas x : asukkaat){
                System.out.println(x.printInfo());
            }
        }
    }
}
