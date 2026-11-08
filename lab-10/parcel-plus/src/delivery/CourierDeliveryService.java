package delivery;

import common.Shipment;

public class CourierDeliveryService extends DeliveryService {
    @Override
    protected Delivery createDelivery() { return new CourierDelivery(); }

    private static class CourierDelivery implements Delivery {
        @Override public String getName() { return "Кур'єрська"; }
        @Override public double calculateCost(Shipment s) { return 80 + 10 * s.getWeightKg(); }
        @Override public int calculateDays(Shipment s) { return 1; }
        @Override public String executor() { return "Кур'єр Іван"; }
    }
}