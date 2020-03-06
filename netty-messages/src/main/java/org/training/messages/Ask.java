package org.training.messages;

public class Ask extends Message {

    private AskParams params;

    public Ask(AskParams params) {
        super();
        this.params = params;
        setType(MessageType.ASK);
    }

    public AskParams getParams() {
        return params;
    }

}
