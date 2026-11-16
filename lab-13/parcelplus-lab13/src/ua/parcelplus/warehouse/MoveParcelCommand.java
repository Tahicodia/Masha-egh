package ua.parcelplus.warehouse;

/** ConcreteCommand: переміщення посилки в інший сектор. */
public class MoveParcelCommand implements WarehouseCommand {
    private final Warehouse warehouse;
    private final String parcelId;
    private final String targetSector;
    private String previousSector;

    public MoveParcelCommand(Warehouse warehouse, String parcelId, String targetSector) {
        this.warehouse = warehouse; this.parcelId = parcelId; this.targetSector = targetSector;
    }

    @Override public void execute() {
        previousSector = warehouse.get(parcelId).getSector();
        warehouse.moveToSector(parcelId, targetSector);
    }

    @Override public void undo() { warehouse.moveToSector(parcelId, previousSector); }

    @Override public String description() {
        return "Переміщення " + parcelId + " -> сектор " + targetSector;
    }
}
