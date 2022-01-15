package oy.tol.tra;

class Invoice extends Identifiable {
    Integer number;
    Integer sum;

    Invoice(Integer number, Integer sum) {
        this.number = number;
        this.sum = sum;
    }

    @Override
    public int compareTo(Identifiable invoice) {
        if (invoice instanceof Invoice) {
            return this.number.compareTo(((Invoice)invoice).number);
        }
        return -1;
    }

    @Override
    public boolean equals(Object invoice) {
        if (invoice instanceof Invoice) {
            return this.number.equals(((Invoice)invoice).number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    Integer getID() {
        return number;
    }
}