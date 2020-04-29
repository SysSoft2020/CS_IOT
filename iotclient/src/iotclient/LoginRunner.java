package iotclient;

public class LoginRunner implements Runnable {
    Login login;
    public LoginRunner(Login x){
        login = x;
    }
    public void run() {
        login.setVisible(true);
    }
}
