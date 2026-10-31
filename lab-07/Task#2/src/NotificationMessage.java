public class NotificationMessage {
    private final String recipient;
    private final String subject;
    private final String body;
    private final String priority;

    public NotificationMessage(String recipient, String subject, String body, String priority) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
        this.priority = priority;
    }

    public String getRecipient() { return recipient; }
    public String getSubject() { return subject; }
    public String getBody() { return body; }
    public String getPriority() { return priority; }
}
