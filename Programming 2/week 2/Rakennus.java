package johanneslampela2;

import java.util.ArrayList;
import java.util.List;

public class Rakennus {

    private double pinta_ala;
    private int huoneiden_lkm;
    private List<Asukas> asukkaat;

    public Rakennus(double pinta_ala, int huoneiden_lkm){
        this.pinta_ala = pinta_ala;
        this.huoneiden_lkm = huoneiden_lkm;
        this.asukkaat = new ArrayList<Asukas>();
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

    public void addAsukas(String nimi, String syntyma_aika){
        this.asukkaat.add(new Asukas(nimi, syntyma_aika));
    }

    public List<Asukas> getAsukkaat(){
        return this.asukkaat;
    }

    @Override
    public String toString(){
        return "Rakennuksen pinta-ala: " + this.pinta_ala + " m^2.\n" + "Rakennuksessa huoneita on: " + this.huoneiden_lkm + " kappaletta.";
    }
}
