import java.util.ArrayList;
import java.util.List;

//Маршрут доставки
//Публічний конструктор виконує дорогу побудову: затримка і запис у журнал
//Метод копі створює глибоку копію без затримки
public class Route {
    private final String name;
    private String date;
    private String courier;
    private final List<Stop> stops = new ArrayList<>();
    private final WeightLimit limit;

    // Дорога побудова (для еталона)
    public Route(String name, int maxKg, String... stopNames) {
        Logger.log("Route", "Побудова маршруту \"" + name + "\" (дорога операція)...");
        try { Thread.sleep(2000); } catch (InterruptedException e) { }
        this.name = name;
        this.limit = new WeightLimit(maxKg);
        for (String s : stopNames) stops.add(new Stop(s));
        Logger.log("Route", "Еталон \"" + name + "\" побудовано");
    }

    // Глибока копія, без повторної побудови
    private Route(Route other) {
        this.name = other.name;
        this.date = other.date;
        this.courier = other.courier;
        this.limit = new WeightLimit(other.limit);
        for (Stop s : other.stops) stops.add(new Stop(s));
    }

    public Route copy(String date, String courier) {
        Route r = new Route(this);
        r.date = date;
        r.courier = courier;
        Logger.log("Route", "Маршрут на " + date + " створено копіюванням (кур'єр " + courier + ")");
        return r;
    }

    public void addStop(String stopName) {
        stops.add(new Stop(stopName));
        Logger.log("Route", "[" + date + "] додано зупинку " + stopName);
    }

    public void setMaxKg(int kg) {
        limit.setMaxKg(kg);
        Logger.log("Route", "[" + date + "] обмеження ваги змінено на " + kg + " кг");
    }

    public void setCourier(String courier) {
        this.courier = courier;
        Logger.log("Route", "[" + date + "] кур'єра змінено на " + courier);
    }

    @Override
    public String toString() {
        return name + " | дата: " + (date == null ? "еталон" : date)
                + " | кур'єр: " + (courier == null ? "-" : courier)
                + " | зупинки: " + stops + " | обмеження: " + limit;
    }
}