package ua.parcelplus.warehouse;

/** Роль у шаблоні: Command. */
public interface WarehouseCommand {
    void execute();
    void undo();
    String description();
}
