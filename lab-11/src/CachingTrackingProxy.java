import java.util.HashMap;
import java.util.Map;

public class CachingTrackingProxy implements TrackingService{
    private final TrackingService real;
    private final Clock clock;
    private final long ttl;
    private final Map<String, String> statuses = new HashMap<>();
    private final Map<String, Long> times = new HashMap<>();

    public CachingTrackingProxy(TrackingService real, Clock clock, long ttl) {
        this.real = real;
        this.clock = clock;
        this.ttl = ttl;
    }

    public String getStatus(String waybill) {
        Long saved = times.get(waybill);
        if (saved != null && clock.now() - saved < ttl) {
            Logger.log("CachingTrackingProxy", "Статус з кешу (" + waybill + ")");
            return statuses.get(waybill);
        }
        String status = real.getStatus(waybill);
        statuses.put(waybill, status);
        times.put(waybill, clock.now());
        return status;
    }
}
