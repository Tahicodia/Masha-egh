public class Address {
    private final String name;
    private final double distanceKm;
    private final boolean urgent;
    private final int trafficMinutes;

    public Address(String name, double distanceKm, boolean urgent, int trafficMinutes) {
        this.name = name;
        this.distanceKm = distanceKm;
        this.urgent = urgent;
        this.trafficMinutes = trafficMinutes;
    }

    public String getName() { return name; }
    public double getDistanceKm() { return distanceKm; }
    public boolean isUrgent() { return urgent; }
    public int getTrafficMinutes() { return trafficMinutes; }
}
