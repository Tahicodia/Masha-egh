package ua.parcelplus.visitor;

/** Element: відправлення, що приймає відвідувача. */
public interface Consignment {
    String waybill();

    void accept(ShipmentVisitor visitor);
}
