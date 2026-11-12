public class ShipmentFacade {
    private final AddressValidator validator = new AddressValidator();
    private final PriceCalculator calculator = new PriceCalculator();
    private final PaymentService payment = new PaymentService();
    private final WaybillGenerator waybills = new WaybillGenerator();
    private final RoutePlanner planner = new RoutePlanner();
    private final Notifier notifier = new Notifier();

    public String register(String channel, String receiver, String address, double weightKg) {
        Logger.log("ShipmentFacade", "Оформлення відправлення для каналу \"" + channel + "\"");
        validator.validate(address);
        double price = calculator.calculate(weightKg);
        payment.charge(price);
        String waybill = waybills.generate();
        planner.plan(address);
        notifier.notifyReceiver(receiver, waybill);
        Logger.log("ShipmentFacade", "Відправлення " + waybill + " оформлено");
        return waybill;
    }
}
