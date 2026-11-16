package ua.parcelplus.intake;

/** ConcreteHandler: нова перевірка, додається в ланцюг без змін в інших ланках. */
public class LithiumBatteryCheck extends ShipmentCheck {
    @Override
    protected String validate(Shipment s) {
        if (s.hasLithiumBattery() && !s.isLithiumCertified()) {
            return "літієві батареї без сертифіката/маркування (потрібне маркування UN3481)";
        }
        return null;
    }
}
