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
        boolean a = msg.authUser("ivica", "alexandria"); //returns boolean true if username matches password
        System.out.println(a);
        JSONObject fieldData = msg.addField("sssddlolodddddoscsosd", 222, -13.21); //adds field to database and returns object with information on new field
        System.out.println(fieldData);
        
        //JSONArray fields = msg.getAllFields();        
        //for (int i = 0; i< fields.size();i++){
          //  JSONObject o = (JSONObject) fields.get(i);
            //System.out.println(o);
        //}
       
        

    }
}