import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrafficAwareStrategy implements RouteStrategy {

    @Override
    public String getName() {
        return "Урахування заторів";
    }

    @Override
    public List<Address> buildRoute(List<Address> addresses) {
        List<Address> route = new ArrayList<>(addresses);
        route.sort(Comparator.comparingInt(Address::getTrafficMinutes));
        return route;
    }
}