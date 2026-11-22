import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UrgentFirstStrategy implements RouteStrategy {

    @Override
    public String getName() {
        return "Пріоритет термінових";
    }

    @Override
    public List<Address> buildRoute(List<Address> addresses) {
        List<Address> route = new ArrayList<>(addresses);
        route.sort(Comparator.comparing((Address a) -> !a.isUrgent())
                .thenComparingDouble(Address::getDistanceKm));
        return route;
    }
}