/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotserver;

/**
 *
 * @author ivica
 */
public class Message {
    boolean isAuthMessage = false;
    boolean isAddFieldMessage = false;
    boolean isRemoveFieldMessage = false;
    boolean isAddWeatherStationToFieldMessage = false;
    boolean isRemoveWeatherStationFromFieldMessage = false;
    boolean isAddWeatherStationDataMessage = false;
    
    String username;
    String password;
    
    String fieldName;
    String fieldId;
    String weatherStationName;
    String weatherStationId;
    String weatherStationData;
    
    public void setMessage_AuthUser(String username,String password){
        this.isAuthMessage = true;
        this.username = username;
        this.password = password;
    }
    
   
    
}
