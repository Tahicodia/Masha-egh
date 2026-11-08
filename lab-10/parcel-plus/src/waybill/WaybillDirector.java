package waybill;

public class WaybillDirector {

    // Типова накладна «Документи»
    public Waybill buildDocuments(WaybillBuilder b, String sender, String recipient, String phone) {
        return b.sender(sender)
                .recipient(recipient, phone)
                .weight(0.5)
                .dimensions("30x22x1 см")
                .returnDocuments()
                .build();
    }

    // Типова накладна «Посилка з накладеним платежем»
    public Waybill buildParcelWithCod(WaybillBuilder b, String sender, String recipient,
                                      String phone, double cod) {
        return b.sender(sender)
                .recipient(recipient, phone)
                .weight(2.0)
                .dimensions("40x30x20 см")
                .cashOnDelivery(cod)
                .packing()
                .build();
    }
}