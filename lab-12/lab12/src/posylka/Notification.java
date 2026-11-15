package posylka;

/** Abstraction: вид сповіщення («що повідомляється»). Тримає міст до каналу. */
public abstract class Notification {
    protected Channel channel;

    protected Notification(Channel channel) { this.channel = channel; }

    /** Канал можна підмінити під час виконання. */
    public void setChannel(Channel channel) { this.channel = channel; }

    protected abstract String buildText();

    public void send(String recipient) {
        Logger.log(getClass().getSimpleName(), "відправка через канал " + channel.getName());
        channel.deliver(recipient, buildText());
    }
}
