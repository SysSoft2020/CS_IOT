package iotserver;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ConnectionHandler extends Thread {
    
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;

    // Constructor 
    public ConnectionHandler(Socket s) throws IOException, ParseException {
        this.s = s;
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

    }

    @Override
    public void run() {

        while (true) {
            try {
                String received = dis.readUTF();
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(received);
                JSONObject message = (JSONObject) obj;
                if (message.containsKey("AUTHUSER")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHUSER");
                    System.out.println(message.toString());
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    System.out.println("Authed new client!");
                    System.out.println(userName);
                    dos.writeBoolean(true);
                    Iotserver.clients.add(s);
                    System.out.println("Sent ACK back");

                    

                } else if (message.containsKey("AUTHSENSOR")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHSENSOR");
                    System.out.println(message.toString());
                    String userName = (String) userDetails.get("sensorName");
                    String password = (String) userDetails.get("password");
                    dos.writeBoolean(true);
                    System.out.println("Authed new sensor!");
                }
                else {
                    System.out.println("Auth failed, closing connection!");
                    s.close();
                    Thread.currentThread().interrupt();
                    
                }
                
                while(true) {
                    received = dis.readUTF();
                    Iterator i = Iotserver.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            System.out.println("Forwarding message from device to client");
                            output.writeUTF(received);
                        } catch (IOException e) {
                            Iotserver.clients.remove(sock);
                        }
                    }
                }
            } catch (IOException exception) {
                Iotserver.clients.remove(this.s);
                System.out.println("Client disconected, terminate thead");
                break; //client has disconnected, terminate this thread
            } catch (ParseException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
