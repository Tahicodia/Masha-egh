public class TelegramChannel implements NotificationChannel{
    @Override
    public boolean send(NotificationMessage message) {
        System.out.println("[TELEGRAM] ChatId: " + message.getRecipient());
        System.out.println("[TELEGRAM] Message: *" + message.getSubject() + "*\n" + message.getBody());
        System.out.println("[TELEGRAM] Status: Sent by Bot API.");
        return true;
    }
}
