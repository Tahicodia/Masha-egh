//Підсистема перевірки адреси
public class AddressValidator {
    public boolean validate(String address){
        Logger.log("AddressValidator", "Адресу \"" + address + "\" перевірено");
        return true;
    }
}
