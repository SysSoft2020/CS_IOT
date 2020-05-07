package iotclient;

public class IotClient {

    public static void main(String args[]) {
        Gui gui = new Gui();
        Thread t1 = new Thread(gui);
        t1.start();
    }
}
