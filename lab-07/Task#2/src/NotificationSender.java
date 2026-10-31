public class NotificationSender {
    public boolean send(NotificationMessage message, NotificationChannel channel){
        return channel.send(message);
    }
}
