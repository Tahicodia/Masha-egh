public abstract class DeliveryProcedure {
    public final void deliver(Parcel parcel){
        log("Початок вручення відправлення " + parcel.getNumber());
        identifyRecipient(parcel);
        checkPayment(parcel);
        inspectContents(parcel);
        handOver(parcel);
        confirm(parcel);
        closeWaybill(parcel);
    }

    protected abstract void identifyRecipient(Parcel parcel);

    protected abstract void confirm(Parcel parcel);

    protected void inspectContents(Parcel parcel) {
        log("[Огляд вмісту] Не проводиться (за замовчуванням)");
    }

    private void checkPayment(Parcel parcel) {
        if (parcel.getCodAmount() > 0) {
            log("[Перевірка оплати] Накладений платіж " + parcel.getCodAmount() + " грн — оплату отримано");
        } else {
            log("[Перевірка оплати] Відправлення оплачене, доплата не потрібна");
        }
    }

    private void handOver(Parcel parcel) {
        log("[Видача] Відправлення " + parcel.getNumber() + " видано отримувачу");
    }

    private void closeWaybill(Parcel parcel) {
        log("[Закриття накладної] Накладну " + parcel.getNumber() + " закрито");
    }

    protected void log(String event) {
        Log.print(getClass().getSimpleName(), event);
    }
}
