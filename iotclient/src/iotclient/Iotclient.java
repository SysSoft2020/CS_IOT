/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotclient;
import org.json.simple.*;
import org.json.simple.parser.*;
/**
 *
 * @author ivica
 */
public class Iotclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Message msg = new Message();
        msg.connectToServer();
        System.out.println(msg.addWeatherStationData(1, 2, 33, 2, 2, 0));
        System.out.println(msg.authUser("ivica", "alexandria"));
        msg.closeConnection();
        

    }
}