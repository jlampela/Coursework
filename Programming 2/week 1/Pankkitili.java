package johanneslampela1;

public class Pankkitili{
    private String tilinumero;
    private String omistaja;
    private double saldo;

    public Pankkitili(String tilinumero, String omistaja, double saldo){
        this.tilinumero = tilinumero;
        this.omistaja = omistaja;
        this.saldo = saldo;
    }

    public String getTilinumero() {
        return this.tilinumero;
    }

    public void setTilinumero(String tilinumero) {
        this.tilinumero = tilinumero;
    }

    public String getOmistaja() {
        return this.omistaja;
    }

    public void setOmistaja(String omistaja) {
        this.omistaja = omistaja;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Checks if given double is correct if so decreases the balance
     * @param maara amount you want to take from the account
     */

    public void otto(double maara){
        if(saldo - maara < 0){
            System.out.println("Saldo ei riitä!");
        }else if(maara <= 0){
            System.out.println("Ei voida ottaa negatiivisia lukuja tai nollaa!");
        }else{
            saldo -= maara;
            System.out.println("Nostettu " + maara + " euroa.");
        }
    }

    /**
     * Checks if given double is correct if so add to the balance
     * @param maara amount you want to add to the account
     */

    public void talletus(double maara){
        if(maara <= 0){
            System.out.println("Ei voi tallettaa negatiivisia lukuja tai nollaa!");
        } else {
            saldo += maara;
            System.out.println("Talletettu " + maara + " euroa.");
        }
    }

    /**
     * Prints account number, name and balance
     */
    @Override
    public String toString(){
        return "Tilinumero: " + tilinumero + "\nOmistaja: " + omistaja + "\nSaldo: " + saldo + " euroa";
    }
}