package variant3_fitnessmembership.domain;

public enum AdditionalService {
    PERSONAL_TRAINER(3000.0);

    private final double price;

    AdditionalService(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
