package iotclient;

import java.io.IOException;

public class iotclient {

    public static void main(String args[]) throws IOException {
        //Gui gui = new Gui();
        //Thread t1 = new Thread(new GuiRunner(gui));
        //t1.start();
        //Thread t2 = new Thread(new SocketListener(gui));
        //t2.start(); 
        Login login = new Login();
        Thread t1 = new Thread(new LoginRunner(login));
        t1.start();
    }
}
