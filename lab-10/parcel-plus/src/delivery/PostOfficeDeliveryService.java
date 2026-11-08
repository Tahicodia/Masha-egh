package delivery;

import common.Shipment;

public class PostOfficeDeliveryService extends DeliveryService {
    @Override
    protected Delivery createDelivery() { return new PostOfficeDelivery(); }

    private static class PostOfficeDelivery implements Delivery {
        public String getName() { return "У відділення"; }
        public double calculateCost(Shipment s) { return 30 + 4 * s.getWeightKg(); }
        public int calculateDays(Shipment s) { return 3; }
        public String executor() { return "Відділення №5"; }
    }
}