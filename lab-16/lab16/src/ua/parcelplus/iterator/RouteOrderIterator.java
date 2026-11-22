package ua.parcelplus.iterator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Concrete Iterator для кур'єра: у порядку маршруту доставки (за номером зупинки). */
final class RouteOrderIterator extends LookaheadIterator {
    private final List<Shipment> ordered = new ArrayList<>();
    private int position;

    RouteOrderIterator(Van van) {
        DepthFirstCursor cursor = new DepthFirstCursor(new ArrayList<LoadNode>(van.pallets()).iterator());
        for (Shipment s = cursor.nextShipment(); s != null; s = cursor.nextShipment()) {
            ordered.add(s);
        }
        ordered.sort(Comparator.comparingInt(Shipment::routeStop));
    }

    @Override
    protected Shipment advance() {
        return position < ordered.size() ? ordered.get(position++) : null;
    }

    @Override
    public String name() {
        return "Маршрут";
    }
}
