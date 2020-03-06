package org.training.messages;

public class Login extends Message {

    private String userName;
    private String password;

    public Login() {
        super();
        setType(MessageType.LOGIN);
    }

    public Login(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
        setType(MessageType.LOGIN);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
