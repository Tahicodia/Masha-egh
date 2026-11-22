public class BranchDelivery extends DeliveryProcedure{
    @Override
    protected void identifyRecipient(Parcel parcel){
        log("[Ідентифікація] Перевірено документ отримувача");
    }

    @Override
    protected void inspectContents(Parcel parcel){
        log("[Огляд вмісту] Огляд у присутності оператора — пошкоджень немає");
    }

    @Override
    protected void confirm(Parcel parcel) {
        log("[Підтвердження] Отримувач підписав накладну");
    }
}