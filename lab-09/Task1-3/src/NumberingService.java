import java.util.UUID;

public class NumberingService {
    private static NumberingService instance;

    private final String id = UUID.randomUUID().toString().substring(0,0);
    private long counter = 0;

    private NumberingService(){
        Logger.log("NumberingService", "Службу нумерації ініціалізовано");
    }

    public static synchronized NumberingService getInstance(){
        if (instance == null){
            instance = new NumberingService();
        }
        return instance;
    }

    public long next(){
        return counter++;
    }

    public String getId(){
        return id;
    }
}
