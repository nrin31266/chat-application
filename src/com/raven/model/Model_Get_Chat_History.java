
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Get_Chat_History {
    private int senderID;
    private int receiverID;

    public Model_Get_Chat_History() {
    }

    public Model_Get_Chat_History(int senderID, int receiverID) {
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
    public JSONObject toJSONObject(){
        JSONObject json= new JSONObject();
        
        try {
            json.put("senderID", senderID);
            json.put("receiverID", receiverID);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
}
