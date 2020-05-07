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
    final ServerGui gui;

    // Constructor 
    public ConnectionHandler(Socket s, ServerGui _gui) throws IOException, ParseException {
        this.gui = _gui;
        this.s = s;
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

    }

    @Override
    public void run() {
        boolean auth = false;
        while (true) {
            try {
                String received = dis.readUTF();
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(received);
                JSONObject message = (JSONObject) obj;
                if (message.containsKey("AUTHUSER")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHUSER");
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    auth = Authenticator.authClient(userName, password);
                    dos.writeBoolean(auth);
                    if (auth) {
                        IotServer.clients.add(s);
                        gui.ClientCounterIncrement();
                    } else {
                        s.close();
                        Thread.currentThread().interrupt();
                    }

                } else if (message.containsKey("AUTHSENSOR")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHSENSOR");
                    String sensorName = (String) userDetails.get("sensorName");
                    String password = (String) userDetails.get("password");
                    boolean sensorAuth = Authenticator.authSensor(sensorName, password);
                    dos.writeBoolean(sensorAuth);
                    if (!sensorAuth) {
                        s.close();
                        Thread.currentThread().interrupt();
                    }
                } else {
                    s.close();
                    Thread.currentThread().interrupt();
                }

                while (true) {
                    received = dis.readUTF();
                    Iterator i = IotServer.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            output.writeUTF(received);
                        } catch (IOException e) {
                            IotServer.clients.remove(sock);
                        }
                    }
                }
            } catch (IOException e) {
                IotServer.clients.remove(this.s);
                if (auth) {
                    gui.ClientCounterDecrement();
                }
                break;
            } catch (ParseException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
