public class PushChannel implements NotificationChannel{
    @Override
    public boolean send(NotificationMessage message){
        System.out.println("[PUSH] DeviseToken: " + message.getRecipient());
        System.out.println("[PUSH] Payload: {\"Title\": \"" + message.getSubject() + "\", \"body\": \"" + message.getBody() + "\" }");
        System.out.println("[PUSH] Status: Sent by Firebase Cloud Messaging");
        return true;
    }
}
