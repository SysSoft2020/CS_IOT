package iotdevice;

import org.json.simple.*;
import java.io.*;
import static java.lang.System.exit;
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

    public void connectToServer() {
        setupConnectionToServer();
    }

    public void closeConnection() {
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
            exit(0);
        }
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

    /**
     * Sends a JSONObject over TCP/IP socket to a server.
     *
     * This method works by first opening a socket connection to a server and
     * then sending a JSONObject over established connection.
     *
     * Use this function when you need to return Boolean value from the server.
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
            result = inputStream.readBoolean();

        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void send(JSONObject data) {
        try {
            outputStream.writeUTF(data.toString());
        } catch (IOException ex) {
            Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
            exit(0);
        }
    }

    /**
     * Authenticates the user based on username and password combination.
     *
     * This method creates JSONObject with key "AUTHUSER" having a value of
     * another JSONObject which contains username and password. Root object is
     * then send to a server via sendWithBooleanReturn() function, which is
     * going to return true if username and password combination is present in a
     * database table.
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

    public boolean authSensor(String sensorName, String password) {
        JSONObject userData = new JSONObject();
        userData.put("sensorName", sensorName);
        userData.put("password", password);
        JSONObject request = new JSONObject();
        request.put("AUTHSENSOR", userData);
        return sendWithBooleanReturn(request);
    }

    /**
     * Adds a field to a database table.
     *
     * This method creates JSONObject with key "FIELDADD" having a value of
     * another JSONObject which contains details for a field which are obtained
     * from the parameters of this method. Root object is then sent to a server
     * via sendWithJsonObjectReturn() function which is going to return all
     * details for the newly created field.
     *
     * @param fieldName Name of the field as a string
     */
    public void addField(String fieldName) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        JSONObject request = new JSONObject();
        request.put("FIELDADD", fieldData);
        send(request);
    }

    /**
     * Adds and binds a weather station to a specified field.
     *
     * This method creates JSONObject with key "ADDWEATHERSTATION" having a
     * value of another JSONObject containing field and other relevant data for
     * newly created weather station.
     *
     * @param fieldName Name of the field to which new weather station is bound
     * @param sensorName Name of the sensor
     */
    void addWeatherStation(String fieldName, String sensorName) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        fieldData.put("sensorName", sensorName);
        JSONObject request = new JSONObject();
        request.put("ADDWEATHERSTATION", fieldData);
        send(request);
    }

    /**
     * Inputs a weather data to a database table.
     *
     * This function creates JSONObject with key "WEATHERSTATIONDATAADD" key
     * having a value of another JSONObject containing weather data which is
     * obtained from the parameters of this function.
     *
     * First parameter, weatherStationId must be a valid ID number of previously
     * present weather station on the system. Otherwise, adding
     * weatherStationData would fail and this function will return false.
     *
     * @param fieldName Name of the field to which new weather station is bound
     * @param sensorName ID number of weather station this data belongs
     * @param latitude Latitude position of a sensor
     * @param longitude Longitude position of a sensor
     * @param temperature Temperature
     * @param barometricPressure Barometric pressure
     * @param windSpeed Wind speed
     * @param relativeHumidity Relative humidity, in percentage
     * @param airQualityIndex Air quality index
     */
    public void addWeatherStationData(String fieldName, String sensorName, double latitude, double longitude, double temperature, double barometricPressure, double windSpeed, double relativeHumidity, double airQualityIndex) {
        JSONObject fieldData = new JSONObject();
        fieldData.put("fieldName", fieldName);
        fieldData.put("sensorName", sensorName);
        fieldData.put("latitude", latitude);
        fieldData.put("longitude", longitude);
        fieldData.put("temperature", temperature);
        fieldData.put("barometricPressure", barometricPressure);
        fieldData.put("windSpeed", windSpeed);
        fieldData.put("relativeHumidity", relativeHumidity);
        fieldData.put("airQualityIndex", airQualityIndex);
        JSONObject request = new JSONObject();
        request.put("ADDWEATHERSTATIONDATA", fieldData);
        send(request);
    }

}
