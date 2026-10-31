public class ViberChannel implements NotificationChannel {
    @Override
    public boolean send(NotificationMessage message) {
        System.out.println("[VIBER] Phone: " + message.getRecipient());
        System.out.println("[VIBER] Message: " + message.getSubject() + " - " + message.getBody());
        System.out.println("[VIBER] Status: Sent by Viber Bot API.");
        return true;
    }
}