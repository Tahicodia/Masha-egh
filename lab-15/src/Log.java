public class Log {
    private static int minutes = 9 * 60;
    public static void print(String source, String event){
        System.out.println("[%02d:%02d] [%s] %s%n", minutes / 60, minutes % 60, source, event);
        minutes++;
    }
}
