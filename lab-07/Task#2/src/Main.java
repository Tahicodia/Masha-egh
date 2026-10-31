public class Main {
    public static void main(String[] args) {
        NotificationSender sender = new NotificationSender();
        NotificationMessage message =
                new NotificationMessage("+380501234567", "Hello", "Your order is ready", "HIGH");

        sender.send(message, new EmailChannel());
        System.out.println();
        sender.send(message, new SmsChannel());
        System.out.println();
        sender.send(message, new ViberChannel());  // новий канал, нуль змін в існуючому коді
    }
}