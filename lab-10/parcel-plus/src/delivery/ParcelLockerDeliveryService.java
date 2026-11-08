package delivery;

import common.Shipment;

public class ParcelLockerDeliveryService extends DeliveryService {
    @Override
    protected Delivery createDelivery() { return new ParcelLockerDelivery(); }

    private static class ParcelLockerDelivery implements Delivery {
        public String getName() { return "У поштомат"; }
        public double calculateCost(Shipment s) { return 40 + 5 * s.getWeightKg(); }
        public int calculateDays(Shipment s) { return 2; }
        public String executor() { return "Поштомат №17"; }
    }
}