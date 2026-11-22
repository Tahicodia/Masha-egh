public class ParcelLockerDelivery extends DeliveryProcedure{
    @Override
    protected void identifyRecipient(Parcel parcel){
        log("[Ідентифікація] Введено код отримання з застосунку");
    }

    @Override
    protected void confirm(Parcel parcel){
        log("[Підтвердження] Комірку закрито — вручення підтверджено автоматично");
    }
}
