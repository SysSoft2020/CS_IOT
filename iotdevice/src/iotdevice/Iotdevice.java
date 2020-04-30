/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotdevice;
import java.util.Random;
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
        String[] sensorNames = {"Measuring station 1","Measuring station2","Measuring station3","Measuring station 4","Measuring station5","Measuring station6"};
        try{
            msg.connectToServer();
            msg.authSensor("sensor1", "password1");
            System.out.println("Now sending!");
            while(true){
                int rnd = new Random().nextInt(fieldNames.length);
                String fieldName = fieldNames[rnd];
                rnd = new Random().nextInt(sensorNames.length);
                msg.addField(fieldName);
                msg.addWeatherStation(fieldName, sensorNames[rnd]);
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
