package variant3_fitnessmembership.ui;

import variant3_fitnessmembership.application.SubscriptionReceipt;

/** Pure Fabrication: форматування тексту договору (презентаційна відповідальність). */
public final class ContractFormatter {

    public String format(SubscriptionReceipt r) {
        return "Договір #" + r.id() + " з " + r.memberName() +
                "\nТариф: " + r.planCode() +
                "\nБазова вартість: " + r.baseAmount() + " грн" +
                "\nЗнижка: " + r.discountAmount() + " грн" +
                "\nДо сплати: " + r.finalCost() + " грн";
    }
}
