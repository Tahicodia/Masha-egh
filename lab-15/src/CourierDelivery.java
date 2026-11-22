public class CourierDelivery extends DeliveryProcedure{
    @Override
    protected void identifyRecipient(Parcel parcel) {
        log("[Ідентифікація] Отримувач назвав SMS-код");
    }

    @Override
    protected void confirm(Parcel parcel) {
        log("[Підтвердження] Кур'єр зробив фото вручення в застосунку");
    }
}
