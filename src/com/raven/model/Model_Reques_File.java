
package com.raven.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Model_Reques_File {
    int fileID;//ID của tệp cần được yêu cầu từ máy chủ
    private long currentLength;//Độ dài hiện tại của tệp.

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public Model_Reques_File() {
    }

    public Model_Reques_File(int fileID, long currentLength) {
        this.fileID = fileID;
        this.currentLength = currentLength;
    }
    public JSONObject toJsonObject(){
        try {
             JSONObject json= new JSONObject();
             
             json.put("fileID", fileID);
             json.put("currentLength", currentLength);
             return json;
        } catch (JSONException e) {
            return null;
        }
    }
}
