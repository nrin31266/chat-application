
package com.raven.model;

import com.raven.app.MessageType;
import java.awt.TrayIcon;
import org.json.JSONException;
import org.json.JSONObject;

public class Model_Receive_Message {
    private MessageType messageType;
    int fromUserID;
    String text;
    private Model_Receive_Image dataImage;
    private Model_Receive_File dataFile;
    
    

   
    
    
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            if(dataImage!= null){
                json.put("dataImage", dataImage.toJsonObject());
            }
            if(dataFile!= null){
                json.put("dataFile", dataFile.toJsonObject());
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }
    public Model_Receive_Message(Object json){
       JSONObject obj= (JSONObject) json; 
        try {
            messageType= MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID=obj.getInt("fromUserID");
            text=obj.getString("text");
            if(!obj.isNull("dataImage")){
                dataImage= new Model_Receive_Image(obj.get("dataImage"));
            }
            if(!obj.isNull("dataFile")){
                dataFile= new Model_Receive_File(obj.get("dataFile"));
            }
            
        } catch (JSONException e) {
            System.err.println(e);
        }
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model_Receive_Image getDataImage() {
        return dataImage;
    }

    public void setDataImage(Model_Receive_Image dataImage) {
        this.dataImage = dataImage;
    }

    public Model_Receive_Message() {
    }

    public Model_Receive_File getDataFile() {
        return dataFile;
    }

    public void setDataFile(Model_Receive_File dataFile) {
        this.dataFile = dataFile;
    }

    
    
    
}
