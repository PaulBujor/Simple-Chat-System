package utility;

public class Message {
    private String user;
    private String message;
    private boolean IPRequest;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
        IPRequest = false;
    }

    public Message(String user, String message, boolean IPRequest) {
        this.user = user;
        this.message = message;
        this.IPRequest = IPRequest;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isIPRequest() {
        return IPRequest;
    }

    public String toString() {
        return String.format("%s: %s", user, message);
    }
}
