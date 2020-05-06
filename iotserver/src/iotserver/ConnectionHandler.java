package iotserver;

import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ConnectionHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    static int clientCounter;

    // Constructor 
    public ConnectionHandler(Socket s) throws IOException, ParseException {
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
                    System.out.println(message.toString());
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    System.out.println(userName);
                    auth = this.authClient(userName, password);
                    dos.writeBoolean(auth);
                    if (auth) {
                        Iotserver.clients.add(s);
                        System.out.println("Authed new client!");
                        //it would be nice if a function could be made like incrementClient(),
                        //to minify the code inside a thead
                        clientCounter = clientCounter + 1;
                        server_gui.jList1.removeAll();
                        DefaultListModel dlm = new DefaultListModel();
                        dlm.addElement(clientCounter);
                        server_gui.jList1.setModel(dlm);
                        //*************************************************
                    } else {
                        s.close();
                        Thread.currentThread().interrupt();
                    }

                } else if (message.containsKey("AUTHSENSOR")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHSENSOR");
                    System.out.println(message.toString());
                    String userName = (String) userDetails.get("sensorName");
                    String password = (String) userDetails.get("password");
                    dos.writeBoolean(true);
                } else {
                    System.out.println("Auth failed, closing connection!");
                    s.close();
                    Thread.currentThread().interrupt();
                }

                while (true) {
                    received = dis.readUTF();
                    Iterator i = Iotserver.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            output.writeUTF(received);
                        } catch (IOException e) {
                            Iotserver.clients.remove(sock);
                        }
                    }
                }
            } catch (IOException exception) {
                Iotserver.clients.remove(this.s);
                System.out.println("Client disconected, terminate thread");
                if (auth) {
                    //it would be nice if a function could be made like decrementClient(),
                    //to minify the code inside a thead
                    clientCounter = clientCounter - 1;
                    server_gui.jList1.removeAll();
                    DefaultListModel dlm = new DefaultListModel();
                    dlm.addElement(clientCounter);
                    server_gui.jList1.setModel(dlm);
                    //*************************************************
                }
                break; //client has disconnected, terminate this thread
            } catch (ParseException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean authClient(String username, String password) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("clients.txt")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);
            String pwd = (String) jsonObject.get(username);
            if (pwd == null) {
                return false;
            }
            return (pwd.equals(password));
        } catch (IOException ex) {
            Logger.getLogger(Iotserver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            return false;
        }
        return false;
    }
}
