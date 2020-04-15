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

    public void run() {
        ui.addSensorToField("Field 1", "Sensor One");
        ui.addSensorToField("Field 1", "Sensor Two");
        ui.addSensorToField("Field 1", "Sensor Three");
        ui.addField("Field 2");
        ui.addSensorDataToField("Field 2", "Sensor 1", "Olaaa");

    }

}
