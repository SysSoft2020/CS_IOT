/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotserver;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ivica
 */
public class Message {
    /* Parameters that are going to get passed to message, so message
    can send itself just by being provided with socket parameters
    */
    final Socket socket;
    final DataInputStream inputStream;
    final DataOutputStream outputStream;
    /*
    All data that is sent between clients and servers will be 
    distinguished using first element of json array.
    */
    JSONArray data = new JSONArray();

    public Message(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.socket = s;
        this.inputStream = dis;
        this.outputStream = dos;
    }

    public Message(JSONArray incomingData) {
        this.data = incomingData;
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
    }

    public Message(String incomingData) {
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
        try {
            JSONParser jsonParser = new JSONParser();
            this.data.add(jsonParser.parse(incomingData));

        } catch (ParseException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void send() {

    }

    private boolean sendWithBooleanReturn() {
        boolean result = false;
        try {

            try {
                outputStream.writeUTF(data.toString());
            } catch (IOException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = inputStream.readBoolean();
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public boolean authUser(String username, String password) {
        data.add("AUTHREQUEST");
        data.add(username);
        data.add(password);
        return sendWithBooleanReturn();
    }

}