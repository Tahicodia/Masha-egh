package posylka;

/** ConcreteObserver: інтернет-магазин (API), оновлює статус замовлення у себе. */
public class OnlineShop implements StatusObserver {
    private final String shopName;

    public OnlineShop(String shopName) { this.shopName = shopName; }

    @Override public String getName() { return "Магазин " + shopName; }

    @Override
    public void update(Shipment s, String oldStatus, String newStatus) {
        Logger.log(getName(), "API: статус замовлення за накладною " + s.getWaybill() + " оновлено на '" + newStatus + "'");
    }
}
