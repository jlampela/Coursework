package johanneslampela5;

public class Kerrostalo extends Rakennus {

    private final String tyyppi = "Kerrostalo";
    
    public Kerrostalo(){
        super();
    }

    public Kerrostalo(int asunto_maara){
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
