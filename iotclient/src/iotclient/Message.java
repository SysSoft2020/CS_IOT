package iotclient;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public void connectToServer(){
        setupConnectionToServer();
    }
    
    public void closeConnection(){
        closeConnectionToServer();
    }
    
    /**
     * Creates connection to a server over TCP/IP protocol.
     * 
     * Port is predetermined to bd 5056
     */
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

    /**
     * Closes socket connection to a server.
     */
    private void closeConnectionToServer() {
        try {
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String listen(){
        try {
            return inputStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error";
    }

    /**
     * Sends a JSONObject over TCP/IP socket to a server.
     * 
     * This method works by first opening a socket connection to a 
     * server and then sending a JSONObject over established connection.
     * 
     * Use this function when you need to return Boolean value from the 
     * server.
     * 
     * @param data JSONObject containing a message data.
     * @return Boolean value, depending on server response.
     */
     private boolean sendWithBooleanReturn(JSONObject data) {
         
        boolean result = false;
        try {
            try {
                outputStream.writeUTF(data.toString());
            } catch (IOException ex) {
                Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            }
            String a = inputStream.readUTF();
            if ("true".equals(a)) result = true;
             
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Sends a JSONObject over TCP/IP socket to a server.
     * 
     * This method works by first opening a socket connection to a 
     * server and then sending a JSONObject over established connection.
     * 
     * Use this function when you want to return multiple values 
     * from the server, i.e. returning a list off all items in a specific 
     * database table.
     * 
     * @param data JSONObject containing a message data.
     * @return JSONArray of JSONObjects containing all information from the server
     */
    private JSONArray sendWithJsonArrayReturn(JSONObject data) {
        System.out.println("Sending to server");
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
        return null;
    }

    /**
     * Sends a JSONObject over TCP/IP socket to a server.
     * 
     * This method works by first opening a socket connection to a 
     * server and then sending a JSONObject over established connection.
     * 
     * Use this function when you want to return only one set of values 
     * from the server, i.e. return all data for one row in a specific database table.
     * @param data
     * @return 
     */
    private JSONObject sendWithJsonObjectReturn(JSONObject data) {
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
        return null;
    }
    
    /**
     * Authenticates the user based on username and password combination.
     * 
     * This method creates JSONObject with key "AUTHUSER" having a value
     * of another JSONObject which contains username and password.
     * Root object is then send to a server via sendWithBooleanReturn()
     * function, which is going to return true if username and password
     * combination is present in a database table.
     * 
     * @param username Username in string format
     * @param password Password in string format
     * @return Boolean value, true only if user is successfully authenticated.  
     */
    public boolean authUser(String username, String password) {
        JSONObject userData = new JSONObject();
        userData.put("username", username);
        userData.put("password", password);
        JSONObject request = new JSONObject();
        request.put("AUTHUSER", userData);
        return sendWithBooleanReturn(request);
    }

    /**
     * Adds a field to a database table.
     * 
     * This method creates JSONObject with key "FIELDADD" having a value
     * of another JSONObject which contains details for a field which 
     * are obtained from the parameters of this method. 
     * Root object is then sent to a server via sendWithJsonObjectReturn()
     * function which is going to return all details for the newly created 
     * field.
     * 
     * @param fieldName Name of the field as a string
     * @param longitude Geographical longitude of the field
     * @param latitude Geographical latitude of the field
     * @return JSONObject with the details of the newly created field.
     */
    public JSONObject addField(String fieldName, double longitude, double latitude) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        fieldData.put("longitude", longitude);
        fieldData.put("latitude", latitude);
        JSONObject request = new JSONObject();
        request.put("FIELDADD", fieldData);

        JSONObject returnData = sendWithJsonObjectReturn(request);
        return returnData;
    }
    
    /**
     * Gets all fields from a database table.
     * 
     * This function creates JSONObject with key "RETURNALLFIELDS" having a
     * value of true. Object is then sent to a server via 
     * sendWithJsonArrayReturn() function which is going to return JSONArray
     * of JSONObjects, one JSONObject for each field in the database.
     * 
     * @return JSONArray containing information for all fields in the database table.
     */
    public JSONArray getAllFields() {
        JSONObject request = new JSONObject();
        request.put("RETURNALLFIELDS", true);
        JSONArray fields = sendWithJsonArrayReturn(request);
        fields = (JSONArray) fields.get(0);
        return fields;
    }
    
    /**
     * Adds and binds a weather station to a specified field.
     * 
     * This method creates JSONObject with key "ADDWEATHERSTATION" having a  
     * value of another JSONObject containing field and other relevant data
     * for newly created weather station.
     * 
     * @param fieldName Name of the field to which new weather station is bound
     * @param longitude Geographical longitude of weather station
     * @param latitude Geographical latitude of weather station
     * @param serialNumber Serial number of weather station
     * @return JSONObject containing data information for newly added weather station.
     */
    JSONObject addWeatherStation(String fieldName, double longitude, double latitude, String serialNumber) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        fieldData.put("latitude", latitude);
        fieldData.put("longitude", longitude);
        fieldData.put("serialNumber", serialNumber);
        JSONObject request = new JSONObject();
        request.put("ADDWEATHERSTATION", fieldData);
        
        JSONObject returnData = sendWithJsonObjectReturn(request);
        return returnData;
    }

    /**
     * Inputs a weather data to a database table.
     * 
     * This function creates JSONObject with key "WEATHERSTATIONDATAADD" key
     * having a value of another JSONObject containing weather data which is 
     * obtained from the parameters of this function.
     * 
     * First parameter, weatherStationId must be a valid ID number
     * of previously present weather station on the system. Otherwise, adding 
     * weatherStationData would fail and this function will return false.
     * 
     * @param weatherStationId ID number of weather station this data belongs
     * @param temperature Temperature
     * @param barometricPressure Barometric pressure
     * @param windSpeed Wind speed
     * @param relativeHumidity Relative humidity, in percentage
     * @param airQualityIndex Air quality index
     * @return Boolean value, true if data is successfully entered in a database.
     */
    public JSONObject addWeatherStationData(double weatherStationId, double temperature, double barometricPressure, double windSpeed, double relativeHumidity, double airQualityIndex) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("weatherStation", weatherStationId);
        fieldData.put("temperature", temperature);
        fieldData.put("barometricPressure", barometricPressure);
        fieldData.put("windSpeed", windSpeed);
        fieldData.put("relativeHumidity", relativeHumidity);
        fieldData.put("airQualityIndex", airQualityIndex);
        JSONObject request = new JSONObject();
        request.put("ADDWEATHERSTATIONDATA", fieldData);
        return sendWithJsonObjectReturn(request);
    }

}