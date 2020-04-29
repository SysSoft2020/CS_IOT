/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotdevice;
import static java.lang.Math.abs;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ivica
 */
public class Iotdevice {
    public static void main(String[] args) {
        Message msg = new Message();
        String[] fieldNames = {"Ruddington Field", "Springfield",
                                "Blue field","Radnom field",
                                "Cotton field", "Corn Field"};
        try{
            msg.connectToServer();
            msg.authSensor("sensor1", "password1");
            System.out.println("Now sending!");
            while(true){
                int randomNum = ThreadLocalRandom.current().nextInt(-40, 40 + 1);
                int rnd = new Random().nextInt(fieldNames.length);
                String fieldName = fieldNames[rnd];
                msg.addField(fieldName);
                //msg.addWeatherStation("fieldNameHere", randomNum*1.2, randomNum/2.12, "serialnumber");
                //msg.addWeatherStationData(randomNum, randomNum*2.1,1012.32, randomNum*1.11, randomNum*1.41, abs(randomNum));
                System.out.println("Sent, now going to sleep.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Iotdevice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        finally{
            msg.closeConnection();
        }
    }
    
}
