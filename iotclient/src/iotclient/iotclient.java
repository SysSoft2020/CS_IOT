package iotclient;

public class iotclient {

    public static void main(String args[]) {
        Gui gui = new Gui();
        Thread t1 = new Thread(gui);
        t1.start();
    }
}
