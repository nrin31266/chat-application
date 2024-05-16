
package com.raven.model;

import org.json.JSONObject;

public class Model_Receive_Message {
    int fromUserID;
    String text;

    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            return json;
        } catch (Exception e) {
            return null;
        }
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

    public Model_Receive_Message() {
    }

    public Model_Receive_Message(Object json){
       JSONObject obj= (JSONObject) json; 
        try {
            fromUserID=obj.getInt("fromUserID");
            text=obj.getString("text");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public Model_Receive_Message(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Model_Receive_Message{" + "fromUserID=" + fromUserID + ", text=" + text + '}';
    }
    
}
