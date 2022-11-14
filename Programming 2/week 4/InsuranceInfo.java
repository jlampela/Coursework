package johanneslampela4;

public class InsuranceInfo {
    
    private Property property;
    private double vakuutusarvo;

    public InsuranceInfo(Property property, double vakuutusarvo){
        this.property = property;
        this.vakuutusarvo = vakuutusarvo;
    }

    public Property getProperty() {
        return this.property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public double getVakuutusarvo() {
        return this.vakuutusarvo;
    }

    public void setVakuutusarvo(double vakuutusarvo) {
        this.vakuutusarvo = vakuutusarvo;
    }
}
