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
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    public static void main(String[] args)
    {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);
        
        try
        {
            FileHandler fh = new FileHandler("serevrLog.log");
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            logger.addHandler(fh);
            
        }
        catch(IOException e)
        {
            logger.log(Level.SEVERE, "File handler not working :(", e.getCause());
        }
        
        
        logger.log(Level.SEVERE, "Sample log"); //testing if log works
    }
    
    public static void test()
    {
        
    }
    
}
