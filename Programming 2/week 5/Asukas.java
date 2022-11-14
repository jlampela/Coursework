package johanneslampela5;

public class Asukas {

    private String nimi;

    public Asukas(){
        this.nimi = null;
    }

    public Asukas(String nimi){
        this.nimi = nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String printInfo(){
        return "Nimi: " + this.nimi;
    }
}
