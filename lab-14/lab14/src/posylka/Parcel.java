package posylka;

/** Посилка, що проходить сортувальний центр. */
public class Parcel {
    private final String id;
    private final double weightKg;
    private final String destination;

    public Parcel(String id, double weightKg, String destination) {
        this.id = id;
        this.weightKg = weightKg;
        this.destination = destination;
    }

    public String getId() { return id; }
    public double getWeightKg() { return weightKg; }
    public String getDestination() { return destination; }

    @Override public String toString() { return id + " (" + weightKg + " кг, " + destination + ")"; }
}
