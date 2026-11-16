package posylka;

/** ConcreteObserver: бухгалтерія, реагує лише на статус «вручено» (накладений платіж). */
public class Accounting implements StatusObserver {
    private final double codAmount;

    public Accounting(double codAmount) { this.codAmount = codAmount; }

    @Override public String getName() { return "Бухгалтерія"; }

    @Override
    public void update(Shipment s, String oldStatus, String newStatus) {
        if ("вручено".equals(newStatus)) {
            Logger.log(getName(), String.format("зафіксовано оплату накладеного платежу %.2f грн за %s", codAmount, s.getWaybill()));
        } else {
            Logger.log(getName(), "статус '" + newStatus + "' не стосується обліку, ігнорує");
        }
    }
}
