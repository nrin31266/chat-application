package com.raven.model;

import com.raven.app.MessageType;
import com.raven.event.EventFileSender;
import com.raven.service.Service;
import io.socket.client.Ack;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Model_File_Sender {

    public Model_Send_Message getMessage() {
        return message;
    }

    public void setMessage(Model_Send_Message message) {
        this.message = message;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public String getFileExtensions() {
        return fileExtensions;
    }

    public void setFileExtensions(String fileExtensions) {
        this.fileExtensions = fileExtensions;
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

    public Model_File_Sender(File file, Socket socket, Model_Send_Message message) throws IOException {
        accFile = new RandomAccessFile(file, "r");
        this.file = file;
        this.socket = socket;
        this.message = message;
        fileExtensions = getExtensions(file.getName());
        fileSize = accFile.length();
    }

    public Model_File_Sender() {
    }
    private String fileName;

    private Model_Send_Message message; //Đối tượng chứa thông điệp cần gửi.
    private int fileID; //ID của tệp trên máy chủ sau khi được lưu trữ.
    private String fileExtensions; //Phần mở rộng của tên tệp.
    private File file; //Đối tượng File đại diện cho tệp cần gửi.
    private long fileSize; //Kích thước của tệp.
    private RandomAccessFile accFile; //Đối tượng RandomAccessFile để đọc dữ liệu từ tệp.
    private Socket socket; // Đối tượng Socket để gửi dữ liệu qua mạng.
    private EventFileSender event; //Sự kiện để theo dõi quá trình gửi tệp.

//readFile(): Phương thức này có chức năng đọc một phần của tệp và trả về dưới dạng 
//mảng byte. Điều này cho phép dữ liệu của tệp được chia nhỏ thành các phần nhỏ hơn để gửi đi một cách 
//hiệu quả và đồng thời tránh việc tải toàn bộ nội dung của tệp vào bộ nhớ.
    public synchronized byte[] readFile() throws IOException { //Đọc một phần của tệp và trả về dưới dạng mảng byte.
        long filepointer = accFile.getFilePointer();
        if (filepointer != fileSize) {
            int max = 2000;
            long length = filepointer + max >= fileSize ? fileSize - filepointer : max;
            byte[] data = new byte[(int) length];
            accFile.read(data);
            return data;
        } else {
            return null;
        }
    }

    public void initSend() throws IOException { //Khởi tạo quá trình gửi tệp bằng cách gửi thông điệp đến máy chủ.
        socket.emit("send_to_user", message.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    int fileID = (int) os[0];
                    int type = (int) os[1];
                    if (type == MessageType.IMAGE.getValue()) {
                        try {
                            startSend(fileID, 4);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(type== MessageType.FILE.getValue()){
                        //Updata sau
                        System.out.println("File will update ");
                        try {
                            startSend(fileID, 3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }
//startSend(int fileID): Phương thức này bắt đầu gửi tệp sau khi nhận được ID của tệp từ máy chủ. 
//Sau khi nhận được ID, tệp sẽ được chia thành các gói dữ liệu nhỏ và gửi lần lượt đến máy chủ.

    public void startSend(int fileID, int type) throws IOException { //Bắt đầu gửi tệp sau khi nhận được ID của tệp từ máy chủ.
        this.fileID = fileID;
        if (event != null) {
            event.onStartSending();
        }
        sendingFile(type);
    }
//sendingFile(): Phương thức này có nhiệm vụ gửi dữ liệu tệp cho máy chủ dưới dạng các gói dữ liệu. 
//Quá trình này sẽ diễn ra cho đến khi toàn bộ tệp được gửi hoàn tất.

    private void sendingFile(int type) throws IOException { //Gửi dữ liệu tệp cho máy chủ dưới dạng các gói dữ liệu.
        Model_Package_Sender data = new Model_Package_Sender();
        data.setType(type);
        data.setFileID(fileID);
        data.setFileName(file.getName());
        data.setFileSize(fileSize+"");
        data.setFileExtension(fileExtensions);
        byte[] bytes = readFile();
        if (bytes != null) {
            data.setData(bytes);
            data.setFinish(false);
        } else {
            data.setFinish(true);
            close();
        }
        socket.emit("send_file", data.toJsonObject(), new Ack() {
            @Override
            public void call(Object... os) {
                if (os.length > 0) {
                    boolean act = (boolean) os[0];
                    if (act) {
                        try {
                            if (!data.isFinish()) {
                                if (event != null) {
                                    event.onSending(getPercentage());
                                }
                                sendingFile(type);
                            } else {
                                //  File send finish
                                Service.getInstance().fileSendFinish(Model_File_Sender.this);
                                if (event != null) {
                                    event.onFinish();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public double getPercentage() throws IOException { //Tính toán phần trăm đã gửi của tệp.
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }

    public void close() throws IOException { //Đóng tệp sau khi gửi hoàn tất.
        accFile.close();
    }

    private String getExtensions(String fileName) { //Trích xuất phần mở rộng của tên tệp.
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    public String getFileName() { //Trích xuất phần mở rộng của tên tệp.
        return file.getName();
    }

    public void addEvent(EventFileSender event) {//Thêm một sự kiện để theo dõi quá trình gửi tệp.
        this.event = event;
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
