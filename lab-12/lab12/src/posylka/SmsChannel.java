package posylka;

/** ConcreteImplementor: канал SMS. */
public class SmsChannel implements Channel {
    @Override public String getName() { return "SMS"; }

    @Override
    public void deliver(String recipient, String text) {
        Logger.log("SmsChannel", "-> " + recipient + ": " + text);
    }
}
