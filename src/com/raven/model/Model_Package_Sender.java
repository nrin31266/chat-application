
package com.raven.model;

import org.json.JSONObject;

public class Model_Package_Sender {
    int fileID;//ID của tệp.
    int type;
    byte[] data;//Mảng byte chứa dữ liệu của gói tin.
    boolean finish;// Biến boolean chỉ ra liệu gói tin đã hoàn thành hay chưa.
    String fileExtension;
    String fileName;
    String fileSize;

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    

    public Model_Package_Sender() {
    }

    public Model_Package_Sender(int fileID, byte[] data, boolean finish, String fileExtension, String fileName, String fileSize, int type) {
        this.fileID = fileID;

        this.data = data;
        this.finish = finish;
        this.fileExtension = fileExtension;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.type = type;
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
            json.put("fileExtension", fileExtension);
            json.put("fileName", fileName);
            json.put("fileSize", fileSize);
            json.put("type", type);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
