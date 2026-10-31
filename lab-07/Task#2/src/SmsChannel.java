public class SmsChannel implements NotificationChannel{
    @Override
    public boolean send(NotificationMessage message){
        String body = message.getBody();
        String shortText = body.length() > 160 ? body.substring(0,157) + "..." : body;
        System.out.println("[SMS] Phone: " + message.getRecipient());
        System.out.println("[SMS] Text: " + shortText);
        System.out.println("[SMS] Status: Sent by GSM port");
        return true;
    }
}
