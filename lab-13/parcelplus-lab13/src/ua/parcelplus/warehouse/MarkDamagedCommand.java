package ua.parcelplus.warehouse;

/** ConcreteCommand: позначення пошкодження. */
public class MarkDamagedCommand implements WarehouseCommand {
    private final Warehouse warehouse;
    private final String parcelId;
    private boolean previousState;

    public MarkDamagedCommand(Warehouse warehouse, String parcelId) {
        this.warehouse = warehouse; this.parcelId = parcelId;
    }

    @Override public void execute() {
        previousState = warehouse.get(parcelId).isDamaged();
        warehouse.setDamaged(parcelId, true);
    }

    @Override public void undo() { warehouse.setDamaged(parcelId, previousState); }

    @Override public String description() { return "Позначення пошкодження " + parcelId; }
}
