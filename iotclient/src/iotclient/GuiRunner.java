package iotclient;

public class GuiRunner implements Runnable {
    Gui gui;
    public GuiRunner(Gui x){
        gui = x;
    }
    public void run() {
        gui.setVisible(true);
    }
}
