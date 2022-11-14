package johanneslampela3;

public class Subscription {
    
    private String lehden_nimi;
    private String tilaajan_nimi;
    private String toimitusosoite;
    private double kuukausihinta;

    public Subscription(String lehden_nimi, String tilaajan_nimi, String toimitusosoite, double kuukausihinta){
        this.lehden_nimi = lehden_nimi;
        this.tilaajan_nimi = tilaajan_nimi;
        this.toimitusosoite = toimitusosoite;
        this.kuukausihinta = kuukausihinta;
    }

    public String getLehden_nimi() {
        return this.lehden_nimi;
    }

    public void setLehden_nimi(String lehden_nimi) {
        this.lehden_nimi = lehden_nimi;
    }

    public String getTilaajan_nimi() {
        return this.tilaajan_nimi;
    }

    public void setTilaajan_nimi(String tilaajan_nimi) {
        this.tilaajan_nimi = tilaajan_nimi;
    }

    public String getToimitusosoite() {
        return this.toimitusosoite;
    }

    public void setToimitusosoite(String toimitusosoite) {
        this.toimitusosoite = toimitusosoite;
    }

    public double getKuukausihinta() {
        return this.kuukausihinta;
    }

    public void setKuukausihinta(double kuukausihinta) {
        this.kuukausihinta = kuukausihinta;
    }

    public void printInvoice(){
        System.out.println("Lehden nimi: " + getLehden_nimi());
        System.out.println("Tilaajan nimi: " + getTilaajan_nimi());
        System.out.println("Toimitusosoite: " + getToimitusosoite());
    }

}
