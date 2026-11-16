package posylka;

/** ConcreteColleague: сканер штрихкодів. */
public class BarcodeScanner extends SortingDevice {
    public BarcodeScanner(SortingMediator mediator) { super("Сканер", mediator); }

    public void scan(Parcel p) {
        Logger.log(getName(), "зчитано штрихкод " + p.getId() + ", напрямок: " + p.getDestination());
        report(SortingEvent.SCANNED, p, p.getDestination());
    }
}
