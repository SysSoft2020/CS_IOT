package iotserver;

import java.io.*;
import static java.lang.System.exit;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class Iotserver {
    public static Vector clients = new Vector();

    public static void main(String[] args) {
        ServerSocket ss;
        try {
            ss = new ServerSocket(5056);
            while (true) {
                Socket s = null;
                try {
                    s = ss.accept();
                    System.out.println("A new client is connected : " + s);
                    Thread t = new ConnectionHandler(s);
                    t.start();
                } catch (IOException | ParseException ex) {
                    Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
                    exit(0);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        }
    }
}
