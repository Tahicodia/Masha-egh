package posylka;

/** Colleague: пристрій знає лише посередника, а не інші пристрої. */
public abstract class SortingDevice {
    protected final SortingMediator mediator;
    private final String name;

    protected SortingDevice(String name, SortingMediator mediator) {
        this.name = name;
        this.mediator = mediator;
    }

    public String getName() { return name; }

    protected void report(SortingEvent event, Parcel parcel, String detail) {
        Logger.log(name, "повідомляє координатору: " + event + (parcel != null ? " [" + parcel.getId() + "]" : "")
                + (detail != null ? " (" + detail + ")" : ""));
        mediator.onEvent(this, event, parcel, detail);
    }
}
