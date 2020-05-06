package iotserver;

import java.io.*;
import static java.lang.System.exit;
import java.util.*;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.json.simple.parser.ParseException;

public class Iotserver {

    public final static Logger logger_ = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void setupLogger()
    {
        LogManager.getLogManager().reset();
        logger_.setLevel(Level.ALL);
        try
        {
            FileHandler fh = new FileHandler("serverLog.log", true); // true makes the file appended if already in existence
            fh.setFormatter(new SimpleFormatter()); // formats the default XML log to simple text
            fh.setLevel(Level.ALL);
            logger_.addHandler(fh);  
        }
        catch(java.io.IOException e)
        {
            logger_.log(Level.SEVERE, "File handler not working :(", e.getCause());
        }
    }
    public static Vector clients = new  Vector();
    public static void main(String[] args) {
        try {
            //connecting server gui to thread and initializing
            ServerGui gui = new ServerGui();
            Thread sg1 = new Thread(gui);
            sg1.start();
            
            //setting up logger
            Iotserver.setupLogger();
            logger_.info("log startup successful");
            // server is listening on port 5056
            ServerSocket ss = new ServerSocket(5056);
            // running infinite loop for getting client request
            logger_.log(Level.SEVERE, "Waiting for connection between client and server to establish");
            
            while (true) {
                Socket s = null;
                try {
                    // socket object to receive incoming client requests
                    s = ss.accept();
                    System.out.println("A new client is connected : " + s);
                    logger_.log(Level.CONFIG, "Connection with client established");
                    // obtaining input and out streams
                    //System.out.println("Assigning new thread for this client");
                    // create a new thread object
                    Thread t = new ConnectionHandler(s,gui);
                    // Invoking the start() method
                    t.start();
                    logger_.log(Level.INFO, "Connection confirmned");
                } catch (IOException ex) {
                    Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
                    exit(0);
                    logger_.log(Level.SEVERE, "IOException Error, details: ",ex.getMessage());
                }
                catch (ParseException ex) {
                    Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
                    logger_.log(Level.SEVERE, "ParseException Error, details: ", ex.getMessage());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        }
        
    }
}
