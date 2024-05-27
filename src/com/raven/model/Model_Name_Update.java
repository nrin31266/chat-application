
package com.raven.model;

import org.json.JSONObject;

public class Model_Name_Update {
    private int userID;
    private String name;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model_Name_Update() {
    }

    public Model_Name_Update(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }
    public JSONObject toJsonObject(){
        JSONObject json= new JSONObject();
        try {
            json.put("userID", userID);
            json.put("name", name);
            return json;
        } catch (Exception e) {
            return null;
        }
        
    }
}
