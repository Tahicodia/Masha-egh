public class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(NotificationMessage message){
        System.out.println("[EMAIL] To: " + message.getRecipient());
        System.out.println("[EMAIL] Subject: " + message.getSubject());
        System.out.println("[EMAIL] Body: " + message.getBody());
        System.out.println("[EMAIL] Status: Sent by SMTP server");
        return true;
    }
}
