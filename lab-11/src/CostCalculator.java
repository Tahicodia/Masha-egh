public class CostCalculator {
    public double calculate(String branch, Scale scale) {
        double cost = 50 + 10 * scale.getWeightKg() + 0.5 * scale.getLengthCm();
        Logger.log("CostCalculator", branch + ": вартість " + String.format("%.2f", cost) + " грн");
        return cost;
    }
}
