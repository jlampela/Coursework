package johanneslampela3;

public class RegularSubscription extends Subscription {

    private int tilauksen_kesto;

    public RegularSubscription(String lehden_nimi, String tilaajan_nimi, String toimitusosoite, double kuukausihinta, int tilauksen_kesto) {
        super(lehden_nimi, tilaajan_nimi, toimitusosoite, kuukausihinta);
        this.tilauksen_kesto = tilauksen_kesto;
    }

    public int getTilauksen_kesto() {
        return this.tilauksen_kesto;
    }

    public void setTilauksen_kesto(int tilauksen_kesto) {
        this.tilauksen_kesto = tilauksen_kesto;
    }

    @Override
    public void printInvoice(){
        System.out.println("Tilaustyyppi: Normaalitilaus");
        super.printInvoice();
        System.out.println("Tilauksen kesto: " + this.tilauksen_kesto + "kk");
        System.out.println("Tilauksen hinta: " + super.getKuukausihinta() * this.tilauksen_kesto + " euroa");
    }
}
