//Підсистема сповіщення отримувача
public class Notifier {
    public void notifyReceiver(String receiver, String waybill) {
        Logger.log("Notifier", "Отримувача " + receiver + " сповіщено про накладну " + waybill);
    }
}
