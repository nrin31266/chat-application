
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;


public class UserIDToJSON {
    private int userID;

    public UserIDToJSON() {
    }

    public UserIDToJSON(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    public JSONObject toJsonObject(){
        JSONObject json = new JSONObject();
        try {
          json.put("userID", userID);
          return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
