package posylka;

/** ConcreteColleague: сортувальник по напрямках. */
public class Sorter extends SortingDevice {
    public Sorter(SortingMediator mediator) { super("Сортувальник", mediator); }

    public void route(Parcel p, String direction) {
        Logger.log(getName(), "направляє " + p.getId() + " у напрямок '" + direction + "'");
        report(SortingEvent.ROUTED, p, direction);
    }
}
