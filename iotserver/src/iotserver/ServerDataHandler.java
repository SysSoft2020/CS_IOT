package iotserver;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ServerDataHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    // Constructor 
    public ServerDataHandler(Socket s) throws IOException {
        this.s = s;
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
        Iotserver.clients.add(s);
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
                    System.out.println(message.toString());
                    String userName = (String) userDetails.get("username");
                    String password = (String) userDetails.get("password");
                    //TODO : Process data accordingly, store to database or display in gui
                    //Database db = new Database();
                    //boolean isUserValid = db.authUser(userName, password);
                    //dos.writeBoolean(isUserValid);
                    dos.writeUTF("true");
                }

                if (message.containsKey("FIELDADD")) {
                    JSONObject fieldDetails = (JSONObject) message.get("FIELDADD");
                    System.out.println(message.toString());
                    String fieldName = (String) fieldDetails.get("fieldName");
                    double latitude = (double) fieldDetails.get("latitude");
                    double longitude = (double) fieldDetails.get("longitude");
                    //TODO : Process data accordingly, store to database or display in gui
                    //Database db = new Database();
                    //JSONObject data = db.addField(fieldName, latitude, longitude);
                    Iterator i = Iotserver.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            output.writeUTF(fieldDetails.toString());
                        } catch (Exception e) {
                            Iotserver.clients.remove(sock);
                        }
                    }

                }

                if (message.containsKey("ADDWEATHERSTATION")) {
                    JSONObject weatherStationDetails = (JSONObject) message.get("ADDWEATHERSTATION");
                    System.out.println(message.toString());
                    String fieldName = (String) weatherStationDetails.get("fieldName");
                    double latitude = (double) weatherStationDetails.get("latitude");
                    double longitue = (double) weatherStationDetails.get("longitude");
                    String serialNumber = (String) weatherStationDetails.get("serialNumber");
                    //TODO : Process data accordingly, store to database or display in gui
                    Iterator i = Iotserver.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            output.writeUTF(weatherStationDetails.toString());
                        } catch (Exception e) {
                            Iotserver.clients.remove(sock);
                        }
                    }
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
                    //Database db = new Database();
                    //boolean x = db.addWeatherStationData(weatherStation, temperature, barometricPressure,windSpeed,relativeHumidity,airQualityIndex);
                    Iterator i = Iotserver.clients.iterator();
                    while (i.hasNext()) {
                        Socket sock = (Socket) i.next();
                        try {
                            DataOutputStream output = new DataOutputStream(sock.getOutputStream());
                            output.writeUTF(weatherStationDataDetails.toString());
                        } catch (Exception e) {
                            Iotserver.clients.remove(sock);
                        }
                    }
                }
                /******PROCESSING LOGIC ENDS*******/

            } catch (Exception exception) {
                Iotserver.clients.remove(this.s);
                System.out.println("Client disconected, terminate thead");
                break; //client has disconnected, terminate this thread
            }
        }
    }

}