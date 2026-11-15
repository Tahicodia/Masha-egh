package posylka;

/** Component: спільний інтерфейс для відправлення і будь-якої групи. */
public interface CargoComponent {
    String getName();
    double getWeight();
    double getDeclaredValue();
    int getShipmentCount();
    void print(String indent);
}
