package ua.parcelplus.visitor;

import java.util.ArrayList;
import java.util.List;

/** Object Structure: набір відправлень; пропускає відвідувача по всіх елементах за один прохід. */
public class ConsignmentBatch {
    private final String id;
    private final List<Consignment> items = new ArrayList<>();

    public ConsignmentBatch(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public void add(Consignment item) {
        items.add(item);
    }

    public void accept(ShipmentVisitor visitor) {
        for (Consignment item : items) {
            item.accept(visitor);
        }
    }

    public List<String> describe() {
        List<String> out = new ArrayList<>();
        for (Consignment item : items) {
            out.add(item.toString());
        }
        return out;
    }
}
