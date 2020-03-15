package iotserver;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class DataHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    // Constructor 
    public DataHandler(Socket s) throws IOException {
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

                /******PROCESSING LOGIC GOES HERE******/
                if (message.containsKey("AUTHUSER")) {
                    JSONObject userDetails = (JSONObject) message.get("AUTHUSER");
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    System.out.println(message.toString());
                    //TODO : Process data accordingly, store to database or display in gui
                    //Database db = new Database();
                    //boolean isUserValid = db.authUser(userName, password);
                    //dos.writeBoolean(isUserValid);
                    dos.writeBoolean(true);
                }

                if (message.containsKey("FIELDADD")) {
                    JSONObject fieldDetails = (JSONObject) message.get("FIELDADD");
                    System.out.println(fieldDetails);
                    String fieldName = (String) fieldDetails.get("fieldName");
                    double latitude = (double) fieldDetails.get("latitude");
                    double longitude = (double) fieldDetails.get("longitude");
                    System.out.println(message.toString());
                    //TODO : Process data accordingly, store to database or display in gui
                    //Database db = new Database();
                    //JSONObject data = db.addField(fieldName, latitude, longitude);
                    dos.writeUTF(fieldDetails.toString()); //just echo out data for now
                }

                if (message.containsKey("ADDWEATHERSTATION")) {
                    JSONObject weatherStationDetails = (JSONObject) message.get("ADDWEATHERSTATION");
                    System.out.println(weatherStationDetails.toString());
                    String fieldName = (String) weatherStationDetails.get("fieldName");
                    double latitude = (double) weatherStationDetails.get("latitude");
                    double longitue = (double) weatherStationDetails.get("longitude");
                    String serialNumber = (String) weatherStationDetails.get("serialNumber");
                    //TODO : Process data accordingly, store to database or display in gui
                    dos.writeUTF(weatherStationDetails.toString()); //just echo out data for now
                }

                if (message.containsKey("ADDWEATHERSTATIONDATA")) {
                    JSONObject weatherStationDataDetails = (JSONObject) message.get("ADDWEATHERSTATIONDATA");
                    System.out.println(weatherStationDataDetails.toString());
                    long weatherStation = (long) weatherStationDataDetails.get("weatherStation");
                    double temperature = (double) weatherStationDataDetails.get("temperature");
                    double barometricPressure = (double) weatherStationDataDetails.get("barometricPressure");
                    double windSpeed = (double) weatherStationDataDetails.get("windSpeed");
                    double relativeHumidity = (double) weatherStationDataDetails.get("relativeHumidity");
                    long airQualityIndex = (long) weatherStationDataDetails.get("airQualityIndex");
                    //TODO : Process data accordingly, store to database or display in gui
                    //Database db = new Database();
                    //boolean x = db.addWeatherStationData(weatherStation, temperature, barometricPressure,windSpeed,relativeHumidity,airQualityIndex);
                    dos.writeBoolean(true); //just send out dummy true bool for now
                }

                if (message.containsKey("RETURNALLFIELDS")) {
                    //TODO : Process data accordingly, store to database or display in gui
                    dos.writeUTF(message.toString()); //just echo out data for now
                }

                /******PROCESSING LOGIC ENDS*******/

            } catch (EOFException exception) {
                break; //client has disconnected, terminate this thread
            } catch (IOException | ParseException ex) {
                Logger.getLogger(DataHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}