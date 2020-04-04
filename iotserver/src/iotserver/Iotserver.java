package iotserver;
import java.io.*;
import java.util.*;
import java.net.*;

public class Iotserver {
    public static Vector clients = new  Vector();
    public static void main(String[] args) throws IOException {
        Database db = new Database();
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(5056);
        // running infinite loop for getting client request 
        while (true) {
            Socket s = null;
            try {
                // socket object to receive incoming client requests 
                s = ss.accept();
                //System.out.println("A new client is connected : " + s);
                // obtaining input and out streams
                //System.out.println("Assigning new thread for this client");
                // create a new thread object 
                Thread t = new ServerDataHandler(s);
                // Invoking the start() method 
                t.start();
            } catch (IOException e) {
            }
        }
        
    }

}