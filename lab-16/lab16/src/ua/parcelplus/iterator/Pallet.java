package ua.parcelplus.iterator;

/** Палета: містить відправлення та мішки. */
public class Pallet extends Container {
    public Pallet(String label) {
        super(label);
    }

    @Override
    String kind() {
        return "Палета";
    }
}
