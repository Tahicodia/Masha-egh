import java.util.List;
import java.util.stream.Collectors;

public class Courier {
    private final String name;
    private RouteStrategy strategy;

    public Courier(String name, RouteStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public void setStrategy(RouteStrategy strategy) {
        Log.print(name, "Принцип змінено: " + this.strategy.getName() + " → " + strategy.getName());
        this.strategy = strategy;
    }

    public void planRoute(List<Address> addresses) {
        Log.print(name, "Побудова маршруту, принцип: " + strategy.getName());
        String route = strategy.buildRoute(addresses).stream()
                .map(Address::getName)
                .collect(Collectors.joining(" → "));
        Log.print(name, "Маршрут: " + route);
    }
}