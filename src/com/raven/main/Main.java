package com.raven.main;

import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.raven.event.EventDownFile;
import com.raven.event.EventImageView;
import com.raven.event.EventMain;
import com.raven.event.PublicEvent;
import com.raven.form.Chat;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.ComponentResizer;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
        init();
    }

    private void init() {
        setTitle("Chat desktop");
        setIconImage(new ImageIcon(getClass().getResource("/com/raven/icon/icon.png")).getImage());
        ComponentResizer com = new ComponentResizer();
        com.registerComponent(this);
        com.setMinimumSize(new Dimension(100, 100));
        com.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        com.setSnapSize(new Dimension(10, 10));
        login.setVisible(true);
        loading.setVisible(false);
        vIew_Image.setVisible(false);
        home.setVisible(false);
        initEvent();
        Service.getInstance().startServer();
    }

    private void initEvent() {
        PublicEvent.getInstance().addEventMain(new EventMain() {
            @Override
            public void showLoading(boolean show) {
                loading.setVisible(show);
            }

            @Override
            public void initChat() {
                home.setVisible(true);

                login.setVisible(false);

                Service.getInstance().getClient().emit("list_user", Service.getInstance().getUser().getUserID());

            }

            @Override
            public void selectUser(Model_User_Account user) {
                home.setUser(user);
            }

            @Override
            public void updateUser(Model_User_Account user) {
                home.updateUser(user);
            }
        });
        PublicEvent.getInstance().addEventImageView(new EventImageView() {
            @Override
            public void viewImage(Icon image) {
                vIew_Image.viewImage(image);
            }

            @Override
            public void saveImage(Icon image) {
                System.out.println("Save Image next update");
            }

        });
        PublicEvent.getInstance().addEventDownFile(new EventDownFile() {
            @Override
            public void downFile(Model_Receive_File data) {
                String filePath = "client_data/" + data.getFileID() + data.getFileExtension();

                // Tạo đối tượng JFileChooser
                JFileChooser fileChooser = new JFileChooser();

                // Thiết lập chế độ hộp thoại lưu tệp
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

                // Lấy tên file từ model và đặt làm tên file mặc định
                String defaultFileName = data.getFileName(); // Đổi thành tên file từ model của bạn

                // Đặt tên file mặc định cho hộp thoại lưu tệp
                fileChooser.setSelectedFile(new File(defaultFileName));

                // Hiển thị hộp thoại và lấy kết quả
                int result = fileChooser.showSaveDialog(Main.this);

                // Xử lý kết quả
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Thực hiện kiểm tra và tạo tên tệp duy nhất trong thư mục được chọn
                    selectedFile = getUniqueFileName(selectedFile);

                    // Thực hiện các thao tác lưu tệp tại selectedFile
                    try {
                        Files.copy(new File(filePath).toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File saved successfully: " + selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        System.err.println("Error saving file: " + ex.getMessage());
                    }
                }
            }

// Hàm để kiểm tra và tạo tên tệp duy nhất trong thư mục được chọn
            private File getUniqueFileName(File selectedFile) {
                File directory = selectedFile.getParentFile();
                String fileName = selectedFile.getName();
                String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                String extension = fileName.substring(fileName.lastIndexOf('.'));
                int counter = 1;
                while (selectedFile.exists()) {
                    String uniqueFileName = baseName + " (" + counter + ")" + extension;
                    selectedFile = new File(directory, uniqueFileName);
                    counter++;
                }
                return selectedFile;
            }

            @Override
            public void localFileReceiver(Model_Receive_File data) {
                String filePath = "client_data/";
                filePath += data.getFileID() + data.getFileExtension();
                //
                System.out.println("FilePath=" + filePath);
                File file = new File(filePath).getAbsoluteFile();
                String absolutePath = file.getAbsolutePath();

                // Tạo lệnh để mở File Explorer và chọn tệp
                String command = "explorer.exe /select," + absolutePath;

                try {
                    // Chạy lệnh
                    Process process = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void localFileSender(Model_File_Sender data) {

                String filePath = data.getFile().getAbsolutePath();
                //
                System.out.println("FilePath=" + filePath);
                File file = new File(filePath).getAbsoluteFile();
                String absolutePath = file.getAbsolutePath();

                // Tạo lệnh để mở File Explorer và chọn tệp
                String command = "explorer.exe /select," + absolutePath;

                try {
                    // Chạy lệnh
                    Process process = Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        border = new javax.swing.JPanel();
        background = new javax.swing.JPanel();
        body = new javax.swing.JLayeredPane();
        loading = new com.raven.form.Loading();
        login = new com.raven.form.Login();
        vIew_Image = new com.raven.form.VIew_Image();
        home = new com.raven.form.Home();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        border.setBackground(new java.awt.Color(229, 229, 229));

        background.setBackground(new java.awt.Color(255, 255, 255));

        body.setLayout(new java.awt.CardLayout());
        body.add(loading, "card5");
        body.add(login, "card4");
        body.setLayer(vIew_Image, javax.swing.JLayeredPane.POPUP_LAYER);
        body.add(vIew_Image, "card3");
        body.add(home, "card2");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout borderLayout = new javax.swing.GroupLayout(border);
        border.setLayout(borderLayout);
        borderLayout.setHorizontalGroup(
            borderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        borderLayout.setVerticalGroup(
            borderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(borderLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(border, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private int pX;
    private int pY;

    public static void main(String args[]) {
        FlatArcIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JLayeredPane body;
    private javax.swing.JPanel border;
    private com.raven.form.Home home;
    private com.raven.form.Loading loading;
    private com.raven.form.Login login;
    private com.raven.form.VIew_Image vIew_Image;
    // End of variables declaration//GEN-END:variables
}
