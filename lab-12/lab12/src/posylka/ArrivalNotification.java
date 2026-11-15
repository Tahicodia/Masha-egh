package posylka;

/** RefinedAbstraction: про прибуття у відділення. */
public class ArrivalNotification extends Notification {
    private final String branch;

    public ArrivalNotification(Channel channel, String branch) { super(channel); this.branch = branch; }

    @Override protected String buildText() { return "Ваша посилка прибула у відділення " + branch + "."; }
}
