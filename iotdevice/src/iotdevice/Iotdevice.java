package iotdevice;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Iotdevice {

    public String randomFieldName() {
        String[] fieldNames = {"Ruddington Field", "Springfield",
            "Blue field", "Radnom field",
            "Cotton field", "Corn Field"};
        int rnd = new Random().nextInt(fieldNames.length);
        return fieldNames[rnd];
    }

    public String randomSensorName() {
        String[] sensorNames = {"Measuring station 1", "Measuring station 2", "Measuring station 3", "Measuring station 4", "Measuring station 5", "Measuring station 6"};
        int rnd = new Random().nextInt(sensorNames.length);
        return sensorNames[rnd];
    }

    public static void main(String[] args) {
        Message msg = new Message();

        try {
            msg.connectToServer();
            msg.authSensor("sensor1", "password1");
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Iotdevice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            msg.closeConnection();
        }
    }

}
