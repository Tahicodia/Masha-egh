package ua.parcelplus.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Concrete Iterator для вантажників: з останньої палети до першої, включно з вкладеними мішками. */
final class UnloadingOrderIterator extends LookaheadIterator {
    private final DepthFirstCursor cursor;

    UnloadingOrderIterator(Van van) {
        List<LoadNode> reversed = new ArrayList<>(van.pallets());
        Collections.reverse(reversed);
        this.cursor = new DepthFirstCursor(reversed.iterator());
    }

    @Override
    protected Shipment advance() {
        return cursor.nextShipment();
    }

    @Override
    public String name() {
        return "Розвантаження";
    }
}
