package posylka;

/** ConcreteImplementor: канал Telegram. */
public class TelegramChannel implements Channel {
    @Override public String getName() { return "Telegram"; }

    @Override
    public void deliver(String recipient, String text) {
        Logger.log("TelegramChannel", "-> " + recipient + ": " + text);
    }
}
