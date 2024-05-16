
package com.raven.model;

import org.json.JSONObject;

public class Model_Send_Message {
    int fromUserID;
    int toUserID;
    String text;

    public Model_Send_Message() {
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

    @Override
    public String toString() {
        return "Model_Send_Message{" + "fromUserID=" + fromUserID + ", toUserID=" + toUserID + ", text=" + text + '}';
    }

    public Model_Send_Message(int fromUserID, int toUserID, String text) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
    }
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            json.put("text", text);
            return json;
        } catch (Exception e) {
            return null;
        }
    }
}
