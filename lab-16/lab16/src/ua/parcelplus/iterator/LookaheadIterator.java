package ua.parcelplus.iterator;

import java.util.NoSuchElementException;

/** Базовий клас: реалізує hasNext/next через один метод advance(). Кожен екземпляр має власний стан обходу. */
abstract class LookaheadIterator implements ShipmentIterator {
    private Shipment buffered;
    private boolean ready;

    /** @return наступне відправлення або null, якщо обхід завершено */
    protected abstract Shipment advance();

    @Override
    public boolean hasNext() {
        if (!ready) {
            buffered = advance();
            ready = true;
        }
        return buffered != null;
    }

    @Override
    public Shipment next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        ready = false;
        return buffered;
    }
}
