
package com.raven.model;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class Model_Receive_Image {
    int fileID;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    int width;
    int height;
    String fileExtension;
    private String fileName;

    public Model_Receive_Image() {
    }
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    

    public Model_Receive_Image(int fileID , String image, int width, int height,String fileExtension, String fileName) {
        this.fileID = fileID;
        this.image=image;
        this.width = width;
        this.height = height;
        this.fileExtension=fileExtension;
        this.fileName=fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public Model_Receive_Image(Object json) {
        JSONObject obj=(JSONObject) json;
        try {
            fileID=obj.getInt("fileID");
            image=obj.getString("image");
            width=obj.getInt("width");
            height=obj.getInt("height");
            fileExtension=obj.getString("fileExtension");
            fileName=obj.getString("fileName");
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
    public JSONObject toJsonObject(){
        try {
            JSONObject json= new JSONObject();
            
            json.put("fileID", fileID);
            json.put("image", image);
            json.put("width", width);
            json.put("height", height);
            json.put("fileExtension", fileExtension);
            json.put("fileName", fileName);
            return json;
            
        } catch (JSONException e) {
            return null;
        }
    }
}
