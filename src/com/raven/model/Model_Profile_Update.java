package com.raven.model;

import org.json.JSONObject;

public class Model_Profile_Update {

    public Model_Profile_Update() {
    }

    public Model_Profile_Update(int userID, String userName, String gender, String phoneNumber, String date, String email, String address) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.email = email;
        this.address = address;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    private int userID;
    private String userName;
    private String gender;
    private String phoneNumber;
    private String date;
    private String email;
    private String address;

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("userID", userID);
            if (userName != null) {
                json.put("userName", userName);
            } else {
                json.put("userName", ""); // Đặt giá trị rỗng nếu userName là null
            }

            if (gender != null) {
                json.put("gender", gender);
            } else {
                json.put("gender", ""); // Đặt giá trị rỗng nếu gender là null
            }

            if (phoneNumber != null) {
                json.put("phoneNumber", phoneNumber);
            } else {
                json.put("phoneNumber", ""); // Đặt giá trị rỗng nếu phoneNumber là null
            }

            if (date != null) {
                json.put("date", date);
            } else {
                json.put("date", ""); // Đặt giá trị rỗng nếu date là null
            }

            if (email != null) {
                json.put("email", email);
            } else {
                json.put("email", ""); // Đặt giá trị rỗng nếu email là null
            }

            if (address != null) {
                json.put("address", address);
            } else {
                json.put("address", ""); // Đặt giá trị rỗng nếu address là null
            }

            return json;
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu cần
        }
        return null;
    }

}
