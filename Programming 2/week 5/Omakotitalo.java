package johanneslampela5;

public class Omakotitalo extends Rakennus {

    private final String tyyppi = "Omakotitalo";
    
    public Omakotitalo(){
        super();
    }

    public Omakotitalo(int asunto_maara){
        super(asunto_maara);
        super.setRakennustyyppi(tyyppi);
    }

    public String getTyyppi(){
        return this.tyyppi;
    }

    @Override
    public void printInfo(){
        super.printInfo();
    }
}