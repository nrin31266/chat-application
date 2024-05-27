
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;


public class Model_Image_Update {
    private int userID;
    private String imageData;

    public Model_Image_Update() {
    }

    public Model_Image_Update(int userID, String imageData) {
        this.userID = userID;
        this.imageData = imageData;
    } 
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
    public JSONObject toJsonObject(){
        JSONObject json= new JSONObject();
        try {
            json.put("userID", userID);
            
            json.put("imageData", imageData);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
}
