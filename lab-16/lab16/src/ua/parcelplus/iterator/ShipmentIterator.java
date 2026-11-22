package ua.parcelplus.iterator;

import java.util.Iterator;

/** Iterator: єдиний інтерфейс обходу відправлень, незалежно від способу обходу. */
public interface ShipmentIterator extends Iterator<Shipment> {
    String name();
}
