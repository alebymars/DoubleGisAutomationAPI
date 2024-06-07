package models;


public class ErrorMessage {
    public String id;
    public String message;

    public ErrorMessage(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }
}