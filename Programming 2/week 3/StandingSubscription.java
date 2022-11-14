package johanneslampela3;

public class StandingSubscription extends Subscription {

    private int aleprosentti;

    public StandingSubscription(String lehden_nimi, String tilaajan_nimi, String toimitusosoite, double kuukausihinta, int aleprosentti) {
        super(lehden_nimi, tilaajan_nimi, toimitusosoite, kuukausihinta);
        this.aleprosentti = aleprosentti;
    }

    public int getAleprosentti() {
        return this.aleprosentti;
    }

    public void setAleprosentti(int aleprosentti) {
        this.aleprosentti = aleprosentti;
    }

    //Laskee tilauksen hinnan
    public double tilausHinta(){
        double ale = aleprosentti;
        return super.getKuukausihinta() * 12 * ((100-ale)/100);
    }

    @Override
    public void printInvoice(){
        System.out.println("Tilaustyyppi: Kestotilaus");
        super.printInvoice();
        System.out.println("Tilauksen kesto: 12kk");
        System.out.println("Tilauksen hinta: " + tilausHinta() + " euroa");
    }
}
