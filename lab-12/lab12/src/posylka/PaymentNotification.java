package posylka;

/** RefinedAbstraction: про необхідність оплати. */
public class PaymentNotification extends Notification {
    private final double amount;

    public PaymentNotification(Channel channel, double amount) { super(channel); this.amount = amount; }

    @Override protected String buildText() { return String.format("Необхідна оплата: %.2f грн.", amount); }
}
