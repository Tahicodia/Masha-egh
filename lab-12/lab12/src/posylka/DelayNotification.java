package posylka;

/** RefinedAbstraction: про затримку. */
public class DelayNotification extends Notification {
    private final int hours;

    public DelayNotification(Channel channel, int hours) { super(channel); this.hours = hours; }

    @Override protected String buildText() { return "Доставку затримано орієнтовно на " + hours + " год."; }
}
