package posylka;

/** ConcreteColleague: ваги. Лише вимірюють і повідомляють; рішення про перевантаження ухвалює координатор. */
public class Scale extends SortingDevice {
    public Scale(SortingMediator mediator) { super("Ваги", mediator); }

    public void weigh(Parcel p) {
        Logger.log(getName(), "виконано зважування " + p.getId() + ": " + p.getWeightKg() + " кг");
        report(SortingEvent.WEIGHED, p, p.getWeightKg() + " кг");
    }
}
