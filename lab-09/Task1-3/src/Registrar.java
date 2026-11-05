public class Registrar {
    private final String name;

    public Registrar(String name){
        this.name = name;
    }

    public void registerShipment(){
        long number = NumberingService.getInstance().next();
        Logger.log(name, "відправлення зареєстровано, накладна №" + number);
    }

    public String getServiceId(){
        return NumberingService.getInstance().getId();
    }
}
