
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;



public class Model_Receive_File {
    private int fileID;
    private String fileName;
    private String fileExtension;
    private String fileSize;

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Model_Receive_File() {
        this.fileName="Test";
    }

    
    public Model_Receive_File(Object json) {
        JSONObject obj=(JSONObject) json;
        try {
            fileID=obj.getInt("fileID");
            fileName=obj.getString("fileName");
            fileExtension=obj.getString("fileExtension");
            fileSize=obj.getString("fileSize");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            
            json.put("fileID", fileID);
            json.put("fileName", fileName);
            json.put("fileExtension", fileExtension);
            json.put("fileSize", fileSize);
            return json;
            
        } catch (Exception e) {
            return null;
        }
    }
    
}

