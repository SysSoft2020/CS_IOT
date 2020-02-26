/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iotdevice;

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
    
    double temperature;
    double windSpeed;
    double barometricPressure;
    double relativeHumidity;
    int airQuality;
    
    public void setMessage_AuthUser(String username,String password){
        this.isAuthMessage = true;
        this.username = username;
        this.password = password;
    }
    
    public void setMessage_AddFieldMessage(String fieldName, String fieldId){
        this.isAddFieldMessage = true;
        this.fieldName = fieldName;
        this.fieldId = fieldId;  
    }
    
    public void setMessage_RemoveFieldMessage(String fieldName, String fieldId){
        this.isRemoveFieldMessage = true;
        this.fieldName = fieldName;
        this.fieldId = fieldId;  
    }
    
    public void setMessage_AddWeatherStationtoField(String weatherStationName,String weatherStationId){
        this.isAddWeatherStationToFieldMessage = true;
        this.weatherStationName = weatherStationName;
        this.weatherStationId = weatherStationId;
    }
    
    public void setMessage_RemoveWeatherStationfromField(String weatherStationName,String weatherStationId){
        this.isAddWeatherStationToFieldMessage = true;
        this.weatherStationName = weatherStationName;
        this.weatherStationId = weatherStationId;
    }
    
    public void setMessage_weatherStationData(String weatherStationData){
        this.isAddWeatherStationDataMessage = true;
        this.weatherStationData = weatherStationData;
    }
}
