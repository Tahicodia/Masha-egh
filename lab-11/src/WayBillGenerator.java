//Підсистема генерації накладної
public class WayBillGenerator {
    private int counter = 0;

    public String generate() {
        String number = "UA-" + (++counter);
        Logger.log("WaybillGenerator", "Накладну " + number + " згенеровано");
        return number;
    }
}
