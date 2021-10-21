package messagesystem.message;

public enum MessageType {
    GET_CLIENTS("GetClients"),
    SAVE_CLIENT("SaveClient");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
