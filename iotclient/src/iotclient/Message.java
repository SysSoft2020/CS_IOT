package iotclient;
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
    final boolean DEBUG = false;
    Socket socket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    /*
    All data that is sent between clients and servers will be 
    distinguished using first element of json array.
    */
    public Message() {
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
    }

    private void setupConnectionToServer() {
        InetAddress ip;
        try {
            try {
                ip = InetAddress.getByName("localhost");
                this.socket = new Socket(ip, 5056);
                this.inputStream = new DataInputStream(this.socket.getInputStream());
                this.outputStream = new DataOutputStream(this.socket.getOutputStream());

            } catch (UnknownHostException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connection to server sucesfull");
    }
    
    private void closeConnectionToServer(){
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void send(JSONArray data) {}

    private void sendToTerminal(JSONArray data) {
        System.out.println(data);
    }

    private boolean sendWithBooleanReturn(JSONObject data) {
    
        boolean result = false;
     
        setupConnectionToServer();
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
        closeConnectionToServer();
        return result;
    }

    private JSONArray sendWithJsonArrayReturn(JSONObject data) {
        System.out.println("Sending to server");
        setupConnectionToServer();
        try {

            try {
                outputStream.writeUTF(data.toString());
            } catch (IOException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                JSONArray result = new JSONArray();

                
                JSONParser jsonParser = new JSONParser();
                result.add((jsonParser.parse(inputStream.readUTF())));
                return result;

            } catch (ParseException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnectionToServer();
        return null;
    }
    
        private JSONObject sendWithJsonObjectReturn(JSONObject data) {
        setupConnectionToServer();
        try {

            try {
                outputStream.writeUTF(data.toString());
            } catch (IOException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                JSONParser jsonParser = new JSONParser();                
                return (JSONObject) jsonParser.parse(inputStream.readUTF());

            } catch (ParseException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnectionToServer();
        return null;
    }

    public boolean authUser(String username, String password) {
        JSONObject userData = new JSONObject();
        userData.put("username", username);
        userData.put("password", password);
        JSONObject request = new JSONObject();
        request.put("AUTHUSER", userData);
        return sendWithBooleanReturn(request);
    }
    
    JSONObject addField(String fieldName, double longitude, double latitude) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        fieldData.put("longitude", longitude);
        fieldData.put("latitude", latitude);
        JSONObject request = new JSONObject();
        request.put("FIELDADD", fieldData);
        
        JSONObject returnData = sendWithJsonObjectReturn(request);
        return returnData;
    }
   
    public boolean addWeatherStationData(String weatherStation, double temperature, double barometricPressure, double windSpeed, double relativeHumidity, int airQualityIndex) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("weatherStation",weatherStation);
        fieldData.put("temperature", temperature);
        fieldData.put("barometricPressure", barometricPressure);
        fieldData.put("windSpeed",windSpeed);
        fieldData.put("relativeHumidity",relativeHumidity);
        fieldData.put("airQualityIndex",airQualityIndex);        
        JSONObject request = new JSONObject();
        request.put("SESNSORDATAADD", fieldData);

        return sendWithBooleanReturn(request);
    }
    
    
    JSONArray getAllFields(){
        JSONObject request = new JSONObject();
        request.put("RETURNALLFIELDS", true);
        JSONArray fields =  sendWithJsonArrayReturn(request);
        fields = (JSONArray) fields.get(0);
        return fields;
    }

}