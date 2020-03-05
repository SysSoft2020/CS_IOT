package iotserver;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    
    @Override
    public void run() {
        try {
            String received = dis.readUTF();
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(received);
            JSONObject message = (JSONObject)obj;

            /******PROCESSING LOGIC GOES HERE******/
                if (message.containsKey("AUTHUSER")){
                    JSONObject userDetails = (JSONObject) message.get("AUTHUSER");
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    Database db = new Database();
                    boolean isUserValid = db.authUser(userName, password);
                    dos.writeBoolean(isUserValid);
                }
                
                if (message.containsKey("FIELDADD")){
                    JSONObject fieldDetails = (JSONObject) message.get("FIELDADD");
                    System.out.println(fieldDetails);
                    String fieldName = (String) fieldDetails.get("fieldName");
                    double latitude = (double) fieldDetails.get("latitude");
                    double longitude = (double) fieldDetails.get("longitude");
                    Database db = new Database();
                    JSONObject data = db.addField(fieldName, latitude, longitude);
                    dos.writeUTF(data.toString());
                }
                
                if (message.containsKey("WEATHERSTATIONDATAADD")){
                    JSONObject fieldDetails = (JSONObject) message.get("WEATHERSTATIONDATAADD");
                    System.out.println(fieldDetails);
                    long  weatherStation =  (long) fieldDetails.get("weatherStation");
                    double temperature = (double) fieldDetails.get("temperature");
                    double barometricPressure = (double) fieldDetails.get("barometricPressure");
                    double windSpeed = (double) fieldDetails.get("windSpeed");
                    double relativeHumidity = (double) fieldDetails.get("relativeHumidity");
                    long airQualityIndex = (long) fieldDetails.get("airQualityIndex");
                    Database db = new Database();
                    boolean x = db.addWeatherStationData(weatherStation, temperature, barometricPressure,windSpeed,relativeHumidity,airQualityIndex);
                    dos.writeBoolean(x);
                }
                
                if (message.containsKey("RETURNALLFIELDS")){
                    Database db = new Database();
                    JSONArray data = db.getFieldData();
                    dos.writeUTF(data.toString());
                }
            
            /******PROCESSING LOGIC ENDS*******/
            

        } catch (IOException | ParseException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}