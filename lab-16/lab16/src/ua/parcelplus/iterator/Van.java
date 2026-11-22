package ua.parcelplus.iterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Concrete Aggregate: машина з палетами. Внутрішня структура недоступна поза пакетом. */
public class Van implements ShipmentCollection {
    private final String id;
    private final List<Pallet> pallets = new ArrayList<>();

    public Van(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public void addPallet(Pallet pallet) {
        pallets.add(pallet);
    }

    List<Pallet> pallets() {
        return Collections.unmodifiableList(pallets);
    }

    public List<String> describeStructure() {
        List<String> out = new ArrayList<>();
        out.add("Машина " + id);
        for (Pallet p : pallets) {
            p.describe(1, out);
        }
        return out;
    }

    @Override
    public ShipmentIterator createRouteIterator() {
        return new RouteOrderIterator(this);
    }

    @Override
    public ShipmentIterator createUnloadingIterator() {
        return new UnloadingOrderIterator(this);
    }

    @Override
    public ShipmentIterator createDeclaredValueIterator() {
        return new DeclaredValueIterator(this);
    }
}
