package delivery;

import common.Shipment;

public interface Delivery {
    String getName();
    double calculateCost(Shipment shipment);
    int calculateDays(Shipment shipment);
    String executor();
}