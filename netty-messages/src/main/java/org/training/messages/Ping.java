package org.training.messages;

public class Ping extends Message {

    public Ping() {
        super();
        setType(MessageType.PING);
    }

}
