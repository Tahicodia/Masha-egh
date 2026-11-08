package packaging;

import common.Logger;
import common.Shipment;

public class PackingStation {
    private static final String SRC = "PackingStation";

    public void pack(Shipment shipment, PackagingFactory factory) {
        Logger.log(SRC, "Пакування " + shipment.getId() + ", комплект: " + factory.kitName());

        Box box = factory.createBox();
        Filler filler = factory.createFiller();
        Label label = factory.createLabel();

        Logger.log(SRC, "Коробка: " + box.describe() + " [" + box.kit() + "]");
        Logger.log(SRC, "Наповнювач: " + filler.describe() + " [" + filler.kit() + "]");
        Logger.log(SRC, "Етикетка: " + label.describe() + " [" + label.kit() + "]");

        boolean same = box.kit().equals(filler.kit()) && box.kit().equals(label.kit());
        Logger.log(SRC, same
                ? "Усі елементи належать до комплекту «" + box.kit() + "»"
                : "ПОМИЛКА: комплект змішаний");
    }
}