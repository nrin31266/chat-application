package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_User_Account {

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Model_User_Account(int userID, String userName, String gender, String image, boolean status) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.image = image;
        this.status = status;
    }

    public Model_User_Account(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            userID = obj.getInt("userID");
            userName = obj.getString("userName");
            gender = obj.getString("gender");
                        if (obj.has("image")) {
                image = obj.getString("image");
            }
            status = obj.getBoolean("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("userID", userID);
            json.put("userName", userName);
            json.put("gender", gender);
            json.put("image", image);
            json.put("status", status);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int userID;
    private String userName;
    private String gender;
    private String image;
    private boolean status;

    @Override
    public String toString() {
        return "Model_User_Account{" + "userID=" + userID + ", userName=" + userName + ", gender=" + gender + ", image=" + image + ", status=" + status + '}';
    }
    
}
