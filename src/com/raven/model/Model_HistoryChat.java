package com.raven.model;

import java.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

public class Model_HistoryChat {

    private int fromUser;
    private int toUser;
    private int type;
    private String txt;
    private String senderFilePath;
    private String receiverFilePath;
    private int fileID;
    private String fileName;
    private String fileSize;
    private String time;  

    public Model_HistoryChat() {
    }

    public Model_HistoryChat(int fromUser, int toUser, int type, String txt, String senderFilePath, String receiverFilePath, int fileID, String fileName, String fileSize) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
        this.txt = txt;
        this.senderFilePath = senderFilePath;
        this.receiverFilePath = receiverFilePath;
        this.fileID = fileID;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
    

    public Model_HistoryChat(Object obj) {
        JSONObject json = (JSONObject) obj;
        try {
            fromUser = json.getInt("fromUser");
            toUser = json.getInt("toUser");
            type = json.getInt("type");
            txt = json.getString("txt");
            senderFilePath = json.getString("senderFilePath");
            receiverFilePath = json.getString("receiverFilePath");
            fileID = json.getInt("fileID");
            fileName = json.getString("fileName");
            fileSize=json.getString("fileSize");
            time = json.getString("time");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getSenderFilePath() {
        return senderFilePath;
    }

    public void setSenderFilePath(String senderFilePath) {
        this.senderFilePath = senderFilePath;
    }

    public String getReceiverFilePath() {
        return receiverFilePath;
    }

    public void setReceiverFilePath(String receiverFilePath) {
        this.receiverFilePath = receiverFilePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("fromUser", fromUser);
            json.put("toUser", toUser);
            json.put("type", type);
            json.put("txt", txt);
            json.put("senderFilePath", senderFilePath);
            json.put("receiverFilePath", receiverFilePath);
            json.put("fileID", fileID);
            json.put("fileName", fileName);
            json.put("fileSize", fileSize);
            json.put("time", time);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    public int getFileID() {
        return fileID;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }


    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    @Override
    public String toString() {
        return "Model_HistoryChat{" + "fromUser=" + fromUser + ", toUser=" + toUser + ", type=" + type + ", txt=" + txt + ", senderFilePath=" + senderFilePath + ", receiverFilePath=" + receiverFilePath + ", fileID=" + fileID + ", fileName=" + fileName + ", fileSize=" + fileSize + ", time=" + time + '}';
    }
    
}
