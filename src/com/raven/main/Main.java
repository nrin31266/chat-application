package com.raven.main;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCobalt2IJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatDraculaContrastIJTheme;
import com.raven.event.EventDownFile;
import com.raven.event.EventImageView;
import com.raven.event.EventMain;
import com.raven.event.PublicEvent;
import com.raven.form.Chat;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.ComponentResizer;
import java.awt.Color;
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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;

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
                title_a.setBackground(new Color(220, 205, 223));

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
            public void viewImage(Icon image, String mode, Model_Receive_Image data) {
                vIew_Image.viewImage(image, mode, data);
            }

            @Override
            public void saveImage(Icon image, String mode, Model_Receive_Image data) {
                String filePath = "client_data/" + data.getFileID() + data.getFileExtension();
                JFileChooser fileChooser = new JFileChooser();
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

            @Override
            public void viewLocation(Icon image, String mode, Model_Receive_Image data) {
                if (mode.equals("sender")) {
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
                } else {
                    String filePath = "client_data/";
                    filePath += data.getFileID() + data.getFileExtension();
                    //
                    System.out.println("FilePath_Relative=" + filePath);
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
            }

        });
        PublicEvent.getInstance().addEventDownFile(new EventDownFile() {
            @Override
            public void downFile(Model_Receive_File data) {
                String filePath = "client_data/" + data.getFileID() + data.getFileExtension();
                JFileChooser fileChooser = new JFileChooser();
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
        title_a = new javax.swing.JPanel();
        title_m = new javax.swing.JPanel();
        cmdClose = new com.raven.component.OptionButton();
        cmdMaximize = new com.raven.component.OptionButton();
        cmdMinimize = new com.raven.component.OptionButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        border.setBackground(new java.awt.Color(229, 229, 229));

        background.setBackground(new java.awt.Color(255, 255, 255));

        body.setLayout(new java.awt.CardLayout());
        body.add(loading, "card5");
        body.add(login, "card4");
        body.setLayer(vIew_Image, javax.swing.JLayeredPane.POPUP_LAYER);
        body.add(vIew_Image, "card3");
        body.add(home, "card2");

        title_a.setBackground(new java.awt.Color(219, 232, 249));

        javax.swing.GroupLayout title_aLayout = new javax.swing.GroupLayout(title_a);
        title_a.setLayout(title_aLayout);
        title_aLayout.setHorizontalGroup(
            title_aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        title_aLayout.setVerticalGroup(
            title_aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        title_m.setBackground(new java.awt.Color(219, 232, 249));
        title_m.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                title_mMouseDragged(evt);
            }
        });
        title_m.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                title_mMousePressed(evt);
            }
        });

        cmdClose.setBackground(new java.awt.Color(204, 0, 51));
        cmdClose.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/close.png"))); // NOI18N
        cmdClose.setMaximumSize(new java.awt.Dimension(18, 18));
        cmdClose.setMinimumSize(new java.awt.Dimension(18, 18));
        cmdClose.setPreferredSize(new java.awt.Dimension(18, 18));
        cmdClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdCloseMouseExited(evt);
            }
        });
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        cmdMaximize.setBackground(new java.awt.Color(178, 187, 200));
        cmdMaximize.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/maximize.png"))); // NOI18N
        cmdMaximize.setMaximumSize(new java.awt.Dimension(34, 18));
        cmdMaximize.setMinimumSize(new java.awt.Dimension(18, 18));
        cmdMaximize.setPreferredSize(new java.awt.Dimension(34, 18));
        cmdMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdMaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdMaximizeMouseExited(evt);
            }
        });
        cmdMaximize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMaximizeActionPerformed(evt);
            }
        });

        cmdMinimize.setBackground(new java.awt.Color(178, 187, 200));
        cmdMinimize.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/minimize.png"))); // NOI18N
        cmdMinimize.setMaximumSize(new java.awt.Dimension(18, 18));
        cmdMinimize.setMinimumSize(new java.awt.Dimension(18, 18));
        cmdMinimize.setPreferredSize(new java.awt.Dimension(18, 18));
        cmdMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdMinimizeMouseExited(evt);
            }
        });
        cmdMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdMinimizeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout title_mLayout = new javax.swing.GroupLayout(title_m);
        title_m.setLayout(title_mLayout);
        title_mLayout.setHorizontalGroup(
            title_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, title_mLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        title_mLayout.setVerticalGroup(
            title_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmdMaximize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmdMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(title_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(title_m, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(title_m, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(title_a, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addGap(0, 0, 0))
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
    private void title_mMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title_mMousePressed
        pX = evt.getX();
        pY = evt.getY();
    }//GEN-LAST:event_title_mMousePressed

    private void title_mMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title_mMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - pX, this.getLocation().y + evt.getY() - pY);
    }//GEN-LAST:event_title_mMouseDragged

    private void cmdCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCloseMouseEntered
        cmdClose.setContentAreaFilled(true);
        cmdClose.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/close_enter.png")));
    }//GEN-LAST:event_cmdCloseMouseEntered

    private void cmdCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCloseMouseExited
        cmdClose.setContentAreaFilled(false);
        cmdClose.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/close.png")));
    }//GEN-LAST:event_cmdCloseMouseExited

    private void cmdMaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMaximizeMouseEntered
        cmdMaximize.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdMaximizeMouseEntered

    private void cmdMaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMaximizeMouseExited
        cmdMaximize.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdMaximizeMouseExited

    private void cmdMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMinimizeMouseEntered
        cmdMinimize.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdMinimizeMouseEntered

    private void cmdMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdMinimizeMouseExited
        cmdMinimize.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdMinimizeMouseExited

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        System.exit(0);
    }//GEN-LAST:event_cmdCloseActionPerformed

    private void cmdMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMinimizeActionPerformed
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_cmdMinimizeActionPerformed

    private void cmdMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdMaximizeActionPerformed
        
        if (this.getExtendedState() == JFrame.NORMAL) {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            this.setExtendedState(JFrame.NORMAL);
        }
    }//GEN-LAST:event_cmdMaximizeActionPerformed

    public static void main(String args[]) {
        FlatArcIJTheme.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Thiết lập giao diện FlatLaf Dark
                    UIManager.setLookAndFeel(new FlatAtomOneLightIJTheme());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JLayeredPane body;
    private javax.swing.JPanel border;
    private com.raven.component.OptionButton cmdClose;
    private com.raven.component.OptionButton cmdMaximize;
    private com.raven.component.OptionButton cmdMinimize;
    private com.raven.form.Home home;
    private com.raven.form.Loading loading;
    private com.raven.form.Login login;
    private javax.swing.JPanel title_a;
    private javax.swing.JPanel title_m;
    private com.raven.form.VIew_Image vIew_Image;
    // End of variables declaration//GEN-END:variables
}
