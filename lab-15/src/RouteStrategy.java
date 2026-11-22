import java.util.List;

public interface RouteStrategy {
    String getName();
    List<Address> buildRoute(List<Address> addresses);
}
