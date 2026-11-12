//Підсистема розрахунку вартості, може використовуватися окремо, без оформлення
public class PriceCalculator {
    public double calculate(double weightKg) {
        double price = 50 + 10 * weightKg;
        Logger.log("PriceCalculator", "Вартість для " + weightKg + " кг: " + price + " грн");
        return price;
    }
}
