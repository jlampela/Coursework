package johanneslampela4;

public class Property {

    private String tyyppi;
    private String sijaintipaikka;

    public Property(String tyyppi, String sijaintipaikka){
        this.tyyppi = tyyppi;
        this.sijaintipaikka = sijaintipaikka;
    }

    public String getTyyppi() {
        return this.tyyppi;
    }

    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }

    public String getSijaintipaikka() {
        return this.sijaintipaikka;
    }

    public void setSijaintipaikka(String sijaintipaikka) {
        this.sijaintipaikka = sijaintipaikka;
    }
}
