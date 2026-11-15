package posylka;

/** Composite (вантажний рейс). */
public class CargoFlight extends CargoGroup {
    public CargoFlight(String name) { super(name); }
    @Override protected String kind() { return "Рейс"; }
}
