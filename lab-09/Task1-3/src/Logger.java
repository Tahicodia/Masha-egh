import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//Виводить події в консоль у форматі [час] [джерело] подія
public class Logger {
    public static void log(String service, String event){
        System.out.println("[" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "]");
    }
}
