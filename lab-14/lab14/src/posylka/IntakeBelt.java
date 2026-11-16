package posylka;

/** ConcreteColleague: стрічка прийому. */
public class IntakeBelt extends SortingDevice {
    private boolean running = false;

    public IntakeBelt(SortingMediator mediator) { super("Стрічка", mediator); }

    public boolean isRunning() { return running; }

    public void start() {
        running = true;
        Logger.log(getName(), "запущена");
    }

    public void stop(String reason) {
        running = false;
        Logger.log(getName(), "ЗУПИНЕНА (" + reason + ")");
    }

    public void feed(Parcel p) {
        if (!running) {
            Logger.log(getName(), "стрічка зупинена, посилка " + p.getId() + " не прийнята");
            return;
        }
        Logger.log(getName(), "прийнято посилку " + p);
        report(SortingEvent.PARCEL_ENTERED, p, null);
    }
}
