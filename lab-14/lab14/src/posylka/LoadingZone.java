package posylka;

/** ConcreteColleague: зона завантаження машин. */
public class LoadingZone extends SortingDevice {
    public LoadingZone(SortingMediator mediator) { super("Зона завантаження", mediator); }

    public void truckArrived(String direction) {
        Logger.log(getName(), "машина на напрямок '" + direction + "' подана");
        report(SortingEvent.TRUCK_ARRIVED, null, direction);
    }

    public void load(Parcel p) {
        Logger.log(getName(), "завантаження почалося: " + p.getId() + " -> " + p.getDestination());
    }
}
