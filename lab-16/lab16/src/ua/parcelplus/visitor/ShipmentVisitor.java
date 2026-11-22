package ua.parcelplus.visitor;

/** Visitor: окрема операція з перевантаженням для кожного типу відправлення. */
public interface ShipmentVisitor {
    void visit(Letter letter);

    void visit(Parcel parcel);

    void visit(Freight freight);

    void visit(InternationalShipment shipment);
}
