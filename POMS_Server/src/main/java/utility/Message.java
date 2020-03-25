package utility;

public class Message {
    private String user;
    private String message;
    private boolean IPRequest;
    private boolean connectedUpdate;

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
        IPRequest = false;
        connectedUpdate = false;
    }

    public Message(String user, String message, boolean IPRequest) {
        this.user = user;
        this.message = message;
        this.IPRequest = IPRequest;
        connectedUpdate = false;
    }

    public Message(String user, String message, boolean IPRequest, boolean connectedUpdate) {
        this.user = user;
        this.message = message;
        this.IPRequest = IPRequest;
        this.connectedUpdate = connectedUpdate;
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

    public boolean isConnectedUpdate() {
        return connectedUpdate;
    }

    public String toString() {
        return String.format("%s: %s", user, message);
    }
}
