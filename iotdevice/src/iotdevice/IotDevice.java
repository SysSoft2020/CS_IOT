package iotdevice;

import static java.lang.System.exit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IotDevice {

    public static void main(String[] args) {
        Message msg = new Message();
        try {
            msg.connectToServer();
            if (!msg.authSensor("sensor1", "password1")) {
                exit(0);
            }
            while (true) {
                try {
                    msg.addWeatherStationData(RandomDataGenerator.fieldName(),RandomDataGenerator.sensorName(),RandomDataGenerator.latitude(),RandomDataGenerator.longitude(),RandomDataGenerator.temperature(),RandomDataGenerator.barometricPressure(),RandomDataGenerator.windSpeed(),RandomDataGenerator.relativeHumidity(),RandomDataGenerator.airQualityIndex());
                    msg.addWeatherStationData("Test Field", "Test Sensor", RandomDataGenerator.latitude(), RandomDataGenerator.longitude(), RandomDataGenerator.temperature(), RandomDataGenerator.barometricPressure(), RandomDataGenerator.windSpeed(), RandomDataGenerator.relativeHumidity(), RandomDataGenerator.airQualityIndex());
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(IotDevice.class.getName()).log(Level.SEVERE, null, ex);
                    exit(0);
                }
            }
        } finally {
            msg.closeConnection();
        }
    }
}
