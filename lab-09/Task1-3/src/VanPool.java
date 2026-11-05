import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VanPool {
    private final int max;
    private final List<Van> all = new ArrayList<>();
    private final Queue<Van> free = new LinkedList<>();
    private final Queue<String> waiting = new LinkedList<>();

    public VanPool(int max) { this.max = max; }

    // Повертає фургон або null, якщо кур'єр став у чергу
    public Van request(String courier) {
        Van van = free.poll();
        if (van == null && all.size() < max) {
            van = new Van("F-" + (all.size() + 1));
            all.add(van);
        }
        if (van == null) {
            waiting.add(courier);
            Logger.log("VanPool", "Вільних фургонів немає, кур'єр " + courier + " очікує в черзі");
            return null;
        }
        van.assign(courier);
        Logger.log("VanPool", "Фургон " + van.getId() + " → кур'єр " + courier);
        return van;
    }

    public void release(Van van) {
        van.unload();
        Logger.log("VanPool", van.getId() + " повернено, розвантажено");
        String next = waiting.poll();
        if (next == null) {
            free.add(van);
        } else {
            van.assign(next);
            Logger.log("VanPool", "Фургон " + van.getId() + " → кур'єр " + next + " (без повторної підготовки)");
        }
    }

    public void printStats() {
        for (Van v : all) {
            Logger.log("VanPool", "Фургон " + v.getId() + ": рейсів " + v.getTrips());
        }
        Logger.log("VanPool", "Всього створено фургонів: " + all.size());
    }
}