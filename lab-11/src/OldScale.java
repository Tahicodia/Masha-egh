public class OldScale implements Scale{
    public double getWeightKg(){
        Logger.log("OldScale", "вага 2.0 кг");
        return 2.0;
    }

    public double getLengthCm() {
        Logger.log("OldScale", "довжина 40.0 см");
        return 40.0;
    }
}
