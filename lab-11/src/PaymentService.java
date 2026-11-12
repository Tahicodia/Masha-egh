//Підсистема прийому оплати
public class PaymentService {
    public void charge(double amount) {
        Logger.log("PaymentService", "Оплату " + amount + " грн прийнято");
    }
}
