package posylka;

/** Implementor: канал доставки («як доставляється»). */
public interface Channel {
    String getName();
    void deliver(String recipient, String text);
}
