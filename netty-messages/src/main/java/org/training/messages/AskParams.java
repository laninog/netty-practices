package org.training.messages;

import java.io.Serializable;

public class AskParams implements Serializable {

    private String auth;

    public AskParams(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }

}
