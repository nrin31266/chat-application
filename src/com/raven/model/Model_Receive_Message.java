
package com.raven.model;

import com.raven.app.MessageType;
import java.awt.TrayIcon;
import org.json.JSONObject;

public class Model_Receive_Message {
    private MessageType messageType;
    int fromUserID;
    String text;
    private Model_Receive_Image dataImage;
    
    

   
    
    
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            if(dataImage!= null){
                json.put("dataImage", dataImage.toJsonObject());
            }
            return json;
        } catch (Exception e) {
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
            
        } catch (Exception e) {
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

    
    
    
}
