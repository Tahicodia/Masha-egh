package variant3_fitnessmembership.domain;
/**Додаткова послуга */
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
