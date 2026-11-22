public class Parcel {
    private final String number;
    private final int codAmount;

    public Parcel(String number, int codAmount) {
        this.number = number;
        this.codAmount = codAmount;
    }

    public String getNumber() {
        return number;
    }

    public int getCodAmount() {
        return codAmount;
    }
}
