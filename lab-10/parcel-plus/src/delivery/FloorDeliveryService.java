package delivery;

import common.Shipment;

public class FloorDeliveryService extends DeliveryService {
    @Override
    protected Delivery createDelivery() { return new FloorDelivery(); }

    private static class FloorDelivery implements Delivery {
        public String getName() { return "На поверх"; }
        public double calculateCost(Shipment s) { return 120 + 12 * s.getWeightKg(); }
        public int calculateDays(Shipment s) { return 1; }
        public String executor() { return "Кур'єр-вантажник Петро"; }
    }
}