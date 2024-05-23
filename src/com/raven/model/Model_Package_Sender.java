
package com.raven.model;

import org.json.JSONObject;

public class Model_Package_Sender {
    int fileID;//ID của tệp.
    byte[] data;//Mảng byte chứa dữ liệu của gói tin.
    boolean finish;// Biến boolean chỉ ra liệu gói tin đã hoàn thành hay chưa.

    public Model_Package_Sender() {
    }

    public Model_Package_Sender(int fileID, byte[] data, boolean finish) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            json.put("fileID", fileID);
            json.put("data", data);
            json.put("finish", finish);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
