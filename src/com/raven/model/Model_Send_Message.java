
package com.raven.model;

import com.raven.app.MessageType;
import org.json.JSONObject;

public class Model_Send_Message {
    
    private MessageType messageType;
    int fromUserID;
    int toUserID;
    String text;
    private Model_File_Sender file;

    public Model_File_Sender getFile() {
        return file;
    }

    public void setFile(Model_File_Sender file) {
        this.file = file;
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

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Model_Send_Message() {
    }

    public Model_Send_Message(MessageType messageType, int fromUserID, int toUserID, String text) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
    }
    

    
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            
            if( messageType==MessageType.FILE||messageType==MessageType.IMAGE){
                json.put("text", file.getFileExtensions());
            }else{
                json.put("text", text);
            }
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Model_Send_Message{" + "messageType=" + messageType + ", fromUserID=" + fromUserID + ", toUserID=" + toUserID + ", text=" + text + '}';
    }

}
