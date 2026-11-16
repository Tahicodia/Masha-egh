package ua.parcelplus.warehouse;

/** ConcreteCommand: завантаження посилки в машину. */
public class LoadParcelCommand implements WarehouseCommand {
    private final Warehouse warehouse;
    private final String parcelId;
    private final String vehicle;
    private String previousSector;

    public LoadParcelCommand(Warehouse warehouse, String parcelId, String vehicle) {
        this.warehouse = warehouse; this.parcelId = parcelId; this.vehicle = vehicle;
    }

    @Override public void execute() {
        previousSector = warehouse.get(parcelId).getSector();
        warehouse.loadToVehicle(parcelId, vehicle);
    }

    @Override public void undo() { warehouse.unloadToSector(parcelId, previousSector); }

    @Override public String description() {
        return "Завантаження " + parcelId + " -> машина " + vehicle;
    }
}
