
package com.raven.model;

import com.raven.app.MessageType;
import java.awt.TrayIcon;
import org.json.JSONObject;

public class Model_Receive_Message {
    int fromUserID;
    String text;

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

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Model_Receive_Message(int fromUserID, String text, MessageType messageType) {
        this.fromUserID = fromUserID;
        this.text = text;
        this.messageType = messageType;
    }

    public Model_Receive_Message() {
    }
    private MessageType messageType;
    
    
    
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
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
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public String toString() {
        return "Model_Receive_Message{" + "fromUserID=" + fromUserID + ", text=" + text + ", messageType=" + messageType + '}';
    }
    
    
}
