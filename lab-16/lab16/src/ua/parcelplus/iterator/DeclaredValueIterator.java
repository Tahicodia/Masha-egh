package ua.parcelplus.iterator;

import java.util.ArrayList;

/** Concrete Iterator для інвентаризації: лише відправлення з оголошеною цінністю. */
final class DeclaredValueIterator extends LookaheadIterator {
    private final DepthFirstCursor cursor;

    DeclaredValueIterator(Van van) {
        this.cursor = new DepthFirstCursor(new ArrayList<LoadNode>(van.pallets()).iterator());
    }

    @Override
    protected Shipment advance() {
        Shipment s = cursor.nextShipment();
        while (s != null && !s.hasDeclaredValue()) {
            s = cursor.nextShipment();
        }
        return s;
    }

    @Override
    public String name() {
        return "Інвентаризація";
    }
}
