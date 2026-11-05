//Одна зупинка маршруту
public class Stop {
    private final String name;

    public Stop(String name) { this.name = name; }

    public Stop(Stop other) { this.name = other.name; }

    @Override
    public String toString() { return name; }
}