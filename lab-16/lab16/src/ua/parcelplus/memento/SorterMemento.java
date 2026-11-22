package ua.parcelplus.memento;

/** Вузький інтерфейс знімка: зовнішні класи бачать лише мітку й час, але не стан. */
public interface SorterMemento {
    String label();

    String createdAt();
}
