package iotclient;

public class iotclient {

    public static void main(String args[]) {
        Gui gui = new Gui();
        Thread t1 = new Thread(new GuiRunner(gui));
        t1.start();
        Thread t2 = new Thread(new SocketListener(gui));
        t2.start();
       
    }
    
}
