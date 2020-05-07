package iotdevice;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {
    
    
    public static String fieldName() {
        String[] fieldNames = {
            "Ruddington Field",
            "Springfield",
            "Blue field",
            "Karlsruhe field",
            "Cotton field",
            "Corn Field",
            "Greenfield",
            "Small field",
            "Copacabana",
            "Wheatfield"
        };
        int rnd = new Random().nextInt(fieldNames.length);
        return fieldNames[rnd];
    }

    public static String sensorName() {
        String[] sensorNames = {
            "Measuring station 1",
            "Measuring station 2",
            "Measuring station 3",
            "Measuring station 4",
            "Measuring station 5",
            "Measuring station 6"
        };
        int rnd = new Random().nextInt(sensorNames.length);
        return sensorNames[rnd];
    }

    public static double latitude() {
        double random = ThreadLocalRandom.current().nextDouble(-90, 90);
        return random;
    }

    public static double longitude() {
        double random = ThreadLocalRandom.current().nextDouble(-90, 90);
        return random;
    }

    public static double temperature() {
        double random = ThreadLocalRandom.current().nextDouble(-40, 40);
        return random;
    }

    public static double barometricPressure() {
        double random = ThreadLocalRandom.current().nextDouble(900, 1100);
        //barometric pressure in mbar
        return random;
    }

    public static double windSpeed() {
        double random = ThreadLocalRandom.current().nextDouble(0, 30);
        //barometric pressure in mbar
        return random;
    }

    public static double relativeHumidity() {
        double random = ThreadLocalRandom.current().nextDouble(0, 100);
        return random;
    }

    public static double airQualityIndex() {
        double random = ThreadLocalRandom.current().nextDouble(0, 500);
        return random;
    }
}
