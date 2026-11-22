package ua.parcelplus.iterator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/** Допоміжний курсор: лінивий обхід у глибину, працює для будь-якої глибини вкладення мішків. */
final class DepthFirstCursor {
    private final Deque<Iterator<LoadNode>> stack = new ArrayDeque<>();

    DepthFirstCursor(Iterator<LoadNode> roots) {
        stack.push(roots);
    }

    /** @return наступне відправлення або null, коли обхід завершено */
    Shipment nextShipment() {
        while (!stack.isEmpty()) {
            Iterator<LoadNode> it = stack.peek();
            if (!it.hasNext()) {
                stack.pop();
                continue;
            }
            LoadNode node = it.next();
            if (node instanceof Shipment s) {
                return s;
            }
            if (node instanceof Container c) {
                stack.push(c.children().iterator());
            }
        }
        return null;
    }
}
