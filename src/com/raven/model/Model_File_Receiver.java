package com.raven.model;

import com.raven.event.EventFileReceiver;
import com.raven.service.Service;
import io.socket.client.Ack;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import org.json.JSONException;
import org.w3c.dom.events.Event;

public class Model_File_Receiver {

    private String fileName;

    int fileID; //ID của tệp.
    File file; // Đối tượng File đại diện cho tệp nhận được.
    long fileSize;//Kích thước của tệp.
    String fileExtention;//Phần mở rộng của tên tệp.
    RandomAccessFile accFile;//Đối tượng RandomAccessFile để ghi dữ liệu vào tệp nhận được.
    Socket socket;//Đối tượng Socket để giao tiếp với máy chủ.
    EventFileReceiver event; //Sự kiện để theo dõi quá trình nhận tệp.
    public final String PATH_FILE = "client_data/";

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileExtention() {
        return fileExtention;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public EventFileReceiver getEvent() {
        return event;
    }

    public void setEvent(EventFileReceiver event) {
        this.event = event;
    }

    public Model_File_Receiver() {
    }

    public Model_File_Receiver(int fileID, Socket socket, EventFileReceiver event) {
        this.fileID = fileID;

        this.socket = socket;
        this.event = event;
    }

    public void initReques() {//Khởi tạo yêu cầu nhận tệp từ máy chủ bằng cách gửi một yêu cầu đến máy chủ thông qua kết nối socket.
        socket.emit("get_file", fileID, new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    try {
                        fileExtention = os[0].toString();
                        fileSize = (int) os[1];
                        file = new File(PATH_FILE + fileID + fileExtention);
                        accFile = new RandomAccessFile(file, "rw");
                        event.onStartReceiving();
                        //start save File
                        startSaveFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startSaveFile() throws IOException, JSONException {//Bắt đầu lưu trữ dữ liệu tệp nhận được từ máy chủ.
        Model_Reques_File data = new Model_Reques_File(fileID, accFile.length());
        socket.emit("reques_file", data.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                try {
                    if (os.length > 0) {
                        byte[] b = (byte[]) os[0];
                        // Write
                        writeFile(b);
                        event.onReceiving(getPrecentage());
                        startSaveFile();
                    } else {
                        close();// Đảm bảo file được đón
                        event.onFinish(new File(PATH_FILE + fileID + fileExtention), fileID, fileExtention, file.getName(), fileSize+"");
                        //remove list
                        Service.getInstance().fileReceiverFinish(Model_File_Receiver.this);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private synchronized long writeFile(byte[] data) throws IOException {//Ghi dữ liệu nhận được vào tệp.
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public double getPrecentage() throws IOException {// Tính toán phần trăm dữ liệu đã nhận được.
        double precentage;
        long filePointer = accFile.getFilePointer();
        precentage = filePointer * 100 / fileSize;
        return precentage;
    }

    public void close() throws IOException {
        if (accFile != null) {
            accFile.close();
        }
    }

}
