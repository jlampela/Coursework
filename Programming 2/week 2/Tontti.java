package johanneslampela2;

public class Tontti{

    private String nimi;
    private String leveyspiiri;
    private String pituuspiiri;
    private double pinta_ala;
    private Rakennus rakennus;
     
    public Tontti(String nimi, String leveyspiiri, String pituuspiiri, double pinta_ala){
        this.nimi = nimi;
        this.leveyspiiri = leveyspiiri;
        this.pituuspiiri = pituuspiiri;
        this.pinta_ala = pinta_ala;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }

    public String getNimi(){
        return this.nimi;
    }

    public void setKoordinaatit(String leveyspiiri, String pituuspiiri){
        this.leveyspiiri = leveyspiiri;
        this.pituuspiiri = pituuspiiri;
    }

    public String getKoordinaatit(){
        return this.leveyspiiri + " " + this.pituuspiiri;
    }

    public void setPintaAla(double pinta_ala){
        this.pinta_ala = pinta_ala;
    }

    public double getPintaAla(){
        return this.pinta_ala;
    }

    public void setRakennus(double pintaala, int huoneidenlkm){
        this.rakennus = new Rakennus(pintaala, huoneidenlkm);
    }

    public Rakennus getRakennus(){
        return this.rakennus;
    }

    @Override
    public String toString(){
        return "Tontin nimi: " + this.nimi + "\n" + "Tontin Leveys- ja pituuspiiri: " + this.leveyspiiri + ", " + this.pituuspiiri + "\n" + "Tontin pinta-ala: " + this.pinta_ala + " m^2";
    }
}