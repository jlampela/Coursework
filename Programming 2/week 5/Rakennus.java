package johanneslampela5;

import java.util.ArrayList;

public class Rakennus {

    private int asunto_maara;
    private String rakennustyyppi;
    private ArrayList<Asunto> asunto;

    public Rakennus(){
        this.asunto_maara = 0;
        this.rakennustyyppi = "Ei määritelty";
        this.asunto = new ArrayList<>();
    }

    public Rakennus(int asunto_maara){
        this.asunto_maara = asunto_maara;
        this.rakennustyyppi = "Ei määritelty";
        this.asunto = new ArrayList<>();
    }

    public void setRakennustyyppi(String rakennustyyppi){
        this.rakennustyyppi = rakennustyyppi;
    }

    public String getRakennustyyppi(){
        return this.rakennustyyppi;
    }

    public void setAsunto_maara(int asunto_maara) {
        this.asunto_maara = asunto_maara;
    }

    public int getAsunto_maara() {
        return this.asunto_maara;
    }

    public ArrayList<Asunto> getAsunto(){
        return this.asunto;
    }

    public void addAsunto(Asunto e){
        asunto.add(e);
    }

    public void printInfo(){
        System.out.println("Rakennuksen asunto määrä: " + this.asunto_maara + " kpl");
        System.out.println("Rakennustyyppi: " + this.rakennustyyppi);
        int i = 1;

        System.out.println("***************");
        for(Asunto x : asunto){
            System.out.println(i++ + "." + " asunnon"+ " tiedot");
            x.printInfo();
            System.out.println("***************");
        }
    }
}
