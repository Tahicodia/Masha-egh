import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EcoRouteStrategy implements RouteStrategy {

    @Override
    public String getName() {
        return "Екологічний маршрут";
    }

    @Override
    public List<Address> buildRoute(List<Address> addresses) {
        List<Address> route = new ArrayList<>(addresses);
        route.sort(Comparator.comparingDouble(this::emission));
        return route;
    }

    private double emission(Address a) {
        return a.getDistanceKm() * 120 + a.getTrafficMinutes() * 30;
    }
}