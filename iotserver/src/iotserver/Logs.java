package iotserver;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

public class Logs {

    public final static java.util.logging.Logger Log = java.util.logging.Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);

    public static void setupLogger() {
        LogManager.getLogManager().reset();
        Log.setLevel(Level.ALL);
        try {
            FileHandler fh = new FileHandler("serverLog.log", true); // true makes the file appended if already in existence
            fh.setFormatter(new SimpleFormatter()); // formats the default XML log to simple text
            fh.setLevel(Level.ALL);
            Log.addHandler(fh);
        } catch (java.io.IOException e) {
            Log.log(Level.SEVERE, "File handler not working :(", e.getCause());
        }
    }
}
