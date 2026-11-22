package ua.parcelplus.iterator;

/** Мішок: може містити відправлення та інші мішки. */
public class Bag extends Container {
    public Bag(String label) {
        super(label);
    }

    @Override
    String kind() {
        return "Мішок";
    }
}
