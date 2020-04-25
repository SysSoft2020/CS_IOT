package iotclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SocketListener implements Runnable {
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;
    Gui ui = null;

    public SocketListener(Gui r) throws IOException {
        try {
            ui = r;
            InetAddress ip = InetAddress.getByName("localhost");
            this.s = new Socket(ip, 5056);
            this.dis = new DataInputStream(s.getInputStream());
            this.dos = new DataOutputStream(s.getOutputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String received = dis.readUTF();
                JSONParser jsonParser = new JSONParser();
                Object obj = jsonParser.parse(received);
                JSONObject message = (JSONObject) obj;
                System.out.println(message);
                /******PROCESSING LOGIC GOES HERE******/

                if (message.containsKey("FIELDADD")) {
                    JSONObject fieldDetails = (JSONObject) message.get("FIELDADD");
                    System.out.println(message.toString());
                    String fieldName = (String) fieldDetails.get("fieldName");
                    double latitude = (double) fieldDetails.get("latitude");
                    double longitude = (double) fieldDetails.get("longitude");
                    //TODO : Process data accordingly, store to database or display in gui
                    ui.addField(fieldName);
                }

                if (message.containsKey("ADDWEATHERSTATION")) {
                    JSONObject weatherStationDetails = (JSONObject) message.get("ADDWEATHERSTATION");
                    System.out.println(message.toString());
                    String fieldName = (String) weatherStationDetails.get("fieldName");
                    double latitude = (double) weatherStationDetails.get("latitude");
                    double longitue = (double) weatherStationDetails.get("longitude");
                    String serialNumber = (String) weatherStationDetails.get("serialNumber");
                    //TODO : Process data accordingly, store to database or display in gui

                }

                if (message.containsKey("ADDWEATHERSTATIONDATA")) {
                    JSONObject weatherStationDataDetails = (JSONObject) message.get("ADDWEATHERSTATIONDATA");
                    System.out.println(message.toString());
                    double weatherStation = (double) weatherStationDataDetails.get("weatherStation");
                    double temperature = (double) weatherStationDataDetails.get("temperature");
                    double barometricPressure = (double) weatherStationDataDetails.get("barometricPressure");
                    double windSpeed = (double) weatherStationDataDetails.get("windSpeed");
                    double relativeHumidity = (double) weatherStationDataDetails.get("relativeHumidity");
                    double airQualityIndex = (double) weatherStationDataDetails.get("airQualityIndex");
                    //TODO : Process data accordingly, store to database or display in gui
                  
                }
                /******PROCESSING LOGIC ENDS*******/

            } catch (IOException | ParseException exception) {
                
                System.exit(0);
            }
        }
    }

}
