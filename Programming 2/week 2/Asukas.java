package johanneslampela2;

public class Asukas {

    private String nimi;
    private String syntymapaiva;

    public Asukas(String nimi, String syntymapaiva){
        this.nimi = nimi;
        this.syntymapaiva = syntymapaiva;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setSyntymapaiva(String syntymapaiva) {
        this.syntymapaiva = syntymapaiva;
    }

    public String getSyntymapaiva() {
        return this.syntymapaiva;
    }

    @Override
    public String toString(){
        return "Nimi: " + this.nimi + "\n" + "Syntymäpäivä: " + this.syntymapaiva;
    }
}
