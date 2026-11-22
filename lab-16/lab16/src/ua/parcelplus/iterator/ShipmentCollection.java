package ua.parcelplus.iterator;

/** Aggregate: колекція, що створює різні ітератори. */
public interface ShipmentCollection {
    ShipmentIterator createRouteIterator();

    ShipmentIterator createUnloadingIterator();

    ShipmentIterator createDeclaredValueIterator();
}
