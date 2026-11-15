package posylka;

/** ConcreteImplementor: канал e-mail. */
public class EmailChannel implements Channel {
    @Override public String getName() { return "e-mail"; }

    @Override
    public void deliver(String recipient, String text) {
        Logger.log("EmailChannel", "-> " + recipient + ": " + text);
    }
}
