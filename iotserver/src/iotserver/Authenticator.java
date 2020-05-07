package iotserver;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Authenticator {
     public static boolean authClient(String username, String password) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("clients.txt")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String pwd = (String) jsonObject.get(username);
            if (pwd == null) {
                return false;
            }
            return (pwd.equals(password));
        } catch (IOException ex) {
            Logger.getLogger(IotServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            return false;
        }
        return false;
    }

    public static boolean authSensor(String sensorId, String password) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("sensors.txt")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            String pwd = (String) jsonObject.get(sensorId);
            if (pwd == null) {
                return false;
            }
            return (pwd.equals(password));
        } catch (IOException ex) {
            Logger.getLogger(IotServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            return false;
        }
        return false;
    }
}
