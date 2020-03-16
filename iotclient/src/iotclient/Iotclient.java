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
        System.out.println(msg.authUser("ivica", "alexandria")); //autenthicate this user
        System.out.println("Starting to listen...");
        try{
            while(true){
                System.out.println(msg.listen());
            }
        }
        finally{
            System.out.println("Closing connection");
            msg.closeConnection();
    }
        
    }
}