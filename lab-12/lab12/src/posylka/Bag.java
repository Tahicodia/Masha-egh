package posylka;

/** Composite (мішок). */
public class Bag extends CargoGroup {
    public Bag(String name) { super(name); }
    @Override protected String kind() { return "Мішок"; }
}
