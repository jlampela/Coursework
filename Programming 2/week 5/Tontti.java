package johanneslampela5;

public class Tontti{

    private String nimi;
    private String osoite;
    private double pinta_ala;
    private Rakennus rakennus;

    public Tontti(){
        this.nimi = null;
        this.osoite = null;
        this.pinta_ala = 0;
        this.rakennus = new Rakennus();
    }
     
    public Tontti(String nimi, String osoite, double pinta_ala, Rakennus rakennus){
        this.nimi = nimi;
        this.osoite = osoite;
        this.pinta_ala = pinta_ala;
        this.rakennus = rakennus;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }

    public String getNimi(){
        return this.nimi;
    }

    public void setOsoite(String osoite){
        this.osoite = osoite;
    }

    public String getOsoite(){
        return this.osoite;
    }

    public void setPintaAla(double pinta_ala){
        this.pinta_ala = pinta_ala;
    }

    public double getPintaAla(){
        return this.pinta_ala;
    }

    public void setRakennus(Rakennus rakennus){
        this.rakennus = rakennus;
    }

    public Rakennus getRakennus(){
        return this.rakennus;
    }
    
    public void printInfo(){
        System.out.println("Tontin nimi: " + this.nimi + "\n" + "Tontin osoite: " + this.osoite + "\n" + "Tontin pinta-ala: " + this.pinta_ala + " m^2");
        System.out.println("***************");
        this.rakennus.printInfo();
    }
}