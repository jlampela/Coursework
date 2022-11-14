package johanneslampela4;

import java.util.ArrayList;

public class InsInfoContainer {

    private ArrayList<InsuranceInfo> vakuutustiedot;

    public InsInfoContainer(){
        this.vakuutustiedot = new ArrayList<>();
    }

    public void lisaaVakuutus(InsuranceInfo vakuutus){
        this.vakuutustiedot.add(vakuutus);
    }

    public void tulostaKaikki(){
        for(InsuranceInfo x : this.vakuutustiedot){
            System.out.println("----------------");
            System.out.println("Kiinteistö tyyppi: " + x.getProperty().getTyyppi());
            System.out.println("Kiinteistö sijainti: " + x.getProperty().getSijaintipaikka());
            System.out.println("Kiinteistön vakuutusarvo: " + x.getVakuutusarvo());
            System.out.println("----------------");
        }
    }

    public void tulostaSuurempi(double raja){
        for(InsuranceInfo x : this.vakuutustiedot){
            if(x.getVakuutusarvo() > raja){
                System.out.println("----------------");
                System.out.println("Kiinteistö tyyppi: " + x.getProperty().getTyyppi());
                System.out.println("Kiinteistö sijainti: " + x.getProperty().getSijaintipaikka());
                System.out.println("Kiinteistön vakuutusarvo: " + x.getVakuutusarvo());
                System.out.println("Käytetty raja: " + raja);
                System.out.println("----------------");
            }         
        }
    }

    public void tulostaPienempi(double raja){
        for(InsuranceInfo x : this.vakuutustiedot){
            if(x.getVakuutusarvo() < raja){
                System.out.println("----------------");
                System.out.println("Kiinteistö tyyppi: " + x.getProperty().getTyyppi());
                System.out.println("Kiinteistö sijainti: " + x.getProperty().getSijaintipaikka());
                System.out.println("Kiinteistön vakuutusarvo: " + x.getVakuutusarvo());
                System.out.println("Käytetty raja: " + raja);
                System.out.println("----------------");
            }
        }
    }

}
