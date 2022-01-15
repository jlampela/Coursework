package oy.tol.tra;

class Payment extends Identifiable {
    Integer number;
    Integer sum;

    Payment(Integer number, Integer sum) {
        this.number = number;
        this.sum = sum;
    }

    @Override
    public int compareTo(Identifiable payment) {
        if (payment instanceof Payment) {
            return this.number.compareTo(((Payment)payment).number);
        }
        return -1;
    }

    @Override
    public boolean equals(Object payment) {
        if (payment instanceof Payment) {
            return this.number.equals(((Payment)payment).number);
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
