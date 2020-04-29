package iotserver;
import java.io.*;
import static java.lang.System.exit;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class Iotserver {
    public static Vector clients = new  Vector();
    public static void main(String[] args) throws IOException {
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(5056);
        // running infinite loop for getting client request 
        while (true) {
            Socket s = null;
            try {
                // socket object to receive incoming client requests 
                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                // obtaining input and out streams
                //System.out.println("Assigning new thread for this client");
                // create a new thread object 
                Thread t = new ConnectionHandler(s);
                // Invoking the start() method 
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
                exit(0);
            } 
            catch (ParseException ex) {
                Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}