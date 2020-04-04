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


/**
 *
 * @author ivica
 */
public class Iotdevice {
    public static void main(String[] args) throws InterruptedException {
        Message msg = new Message();
        String[] fieldNames = {"Ruddington Field", "Springfield",
                                "Blue field","Radnom field",
                                "Cotton field", "Corn Field"};
        try{
            msg.connectToServer();
            while(true){
                int randomNum = ThreadLocalRandom.current().nextInt(-40, 40 + 1);
                int rnd = new Random().nextInt(fieldNames.length);
                String fieldName = fieldNames[rnd];
                msg.addField(fieldName, randomNum, randomNum/2);
                msg.addWeatherStation("fieldNameHere", randomNum*1.2, randomNum/2.12, "serialnumber");
                msg.addWeatherStationData(randomNum, randomNum*2.1,1012.32, randomNum*1.11, randomNum*1.41, abs(randomNum));
                System.out.println("Sent, now going to sleep.");
                TimeUnit.SECONDS.sleep(1);
            }
        }
        finally{
            msg.closeConnection();
        }
    }
    
}
