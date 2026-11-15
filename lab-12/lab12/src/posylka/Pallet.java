package posylka;

/** Composite (палета). */
public class Pallet extends CargoGroup {
    public Pallet(String name) { super(name); }
    @Override protected String kind() { return "Палета"; }
}
