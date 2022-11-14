package johanneslampela5;

public class Rivitalo extends Rakennus {

    private final String tyyppi = "Rivitalo";
    
    public Rivitalo(){
        super();
    }

    public Rivitalo(int asunto_maara){
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
