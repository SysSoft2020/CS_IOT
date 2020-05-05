/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotserver;
import java.util.logging.*;
import java.io.IOException;

/**
 *
 * @author Sami Ljimari
 */


public class logger {
    private final static Logger logger_ = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private static void setupLogger()
    {
        LogManager.getLogManager().reset();
        logger_.setLevel(Level.ALL);
        
        
        try
        {
            FileHandler fh = new FileHandler("serverLog.log", true); // true makes the file appended if already in existence
            fh.setFormatter(new SimpleFormatter()); // formats the default XML log to simple text
            fh.setLevel(Level.ALL);
            logger_.addHandler(fh);
            
        }
        catch(java.io.IOException e)
        {
            logger_.log(Level.SEVERE, "File handler not working :(", e);
        }
    }
    
    public static void main(String[] args)
    { 
        //logger.log(Level.SEVERE, "Sample log"); //testing if log works
        logger.setupLogger();
        logger_.info("log startup successful");
        
        try
        {
            throw new java.io.IOException("Couldn't read file");
        }
        catch(java.io.IOException e)
        {
            logger_.log(Level.SEVERE, "File read error", e);
        }
    }
    
    public static void test()
    {
        
    }
    
}
