public class Clock {
    private long now = 0;
    public long now(){
        return now;
    }
    public void advance(long seconds){
        now += seconds;
    }
}
