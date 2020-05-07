package iotserver;

import java.io.*;
import static java.lang.System.exit;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import org.json.simple.parser.ParseException;

public class IotServer {

    public static Vector clients = new Vector();

    public static void main(String[] args) {
        try {
            ServerGui gui = new ServerGui();
            Thread sg1 = new Thread(gui);
            sg1.start();
            Logs.setupLogger();
            Logs.Log.info("log startup successful");
            ServerSocket ss = new ServerSocket(5056);
            while (true) {
                Socket s = null;
                try {
                    s = ss.accept();
                    Logs.Log.log(Level.CONFIG, "Connection with client established");
                    Thread t = new ConnectionHandler(s, gui);
                    t.start();
                } catch (IOException ex) {
                    exit(0);
                } catch (ParseException ex) {
                }
            }
        } catch (IOException ex) {
            exit(0);
        }
    }
}
