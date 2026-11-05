//Обмеження за вагою
public class WeightLimit {
    private int maxKg;

    public WeightLimit(int maxKg) { this.maxKg = maxKg; }

    public WeightLimit(WeightLimit other) { this.maxKg = other.maxKg; }

    public void setMaxKg(int maxKg) { this.maxKg = maxKg; }

    @Override
    public String toString() { return maxKg + " кг"; }
}
