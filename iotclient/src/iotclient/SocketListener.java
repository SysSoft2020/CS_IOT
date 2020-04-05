/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotclient;

import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivica
 */
public class SocketListener implements Runnable {

    Gui ui = null;

    public SocketListener(Gui r) {
        ui = r;
    }
    
    public void run(){
        ui.fields.put("Field 7", new HashMap<String,Vector>());
        ui.populateComboBox();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        ui.fields.put("Field 8", new HashMap<String,Vector>());
        ui.populateComboBox();


    }

}
