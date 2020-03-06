package org.training.messages;

import java.io.Serializable;

public class Message implements Serializable {

    private MessageType type;
    private String clientId;

    public Message() {
        this.clientId = Constants.getClientId();
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
