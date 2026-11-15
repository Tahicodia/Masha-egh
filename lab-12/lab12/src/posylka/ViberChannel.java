package posylka;

/** ConcreteImplementor: канал Viber. */
public class ViberChannel implements Channel {
    @Override public String getName() { return "Viber"; }

    @Override
    public void deliver(String recipient, String text) {
        Logger.log("ViberChannel", "-> " + recipient + ": " + text);
    }
}
