import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShortestDistanceStrategy implements RouteStrategy{
    @Override
    public String getName() {
        return "Найкоротша відстань";
    }

    @Override
    public List<Address> buildRoute(List<Address> addresses) {
        List<Address> route = new ArrayList<>(addresses);
        route.sort(Comparator.comparingDouble(Address::getDistanceKm));
        return route;
    }
}
