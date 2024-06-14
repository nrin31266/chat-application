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
import com.raven.event.EventProfile;
import com.raven.event.PublicEvent;
import com.raven.form.Chat;
import com.raven.form.Home;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Image_Update;
import com.raven.model.Model_Name_Update;
import com.raven.model.Model_Profile;
import com.raven.model.Model_Profile_Update;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.model.Model_User_Account;
import com.raven.model.UserIDToJSON;
import com.raven.service.Service;
import com.raven.swing.ComponentResizer;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLookAndFeel;
import net.coobird.thumbnailator.Thumbnails;
import org.json.JSONObject;

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
        com.setMinimumSize(new Dimension(660, 460));
        com.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
        com.setSnapSize(new Dimension(10, 10));
        login.setVisible(true);
        loading.setVisible(false);
        vIew_Image.setVisible(false);
        home.setVisible(false);
        cmdMaximize.setVisible(false);
        view_Profile.setVisible(false);

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
                Service.getInstance().getClient().emit("list_user", Service.getInstance().getUser().getUserID());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                home.setVisible(true);

                login.setVisible(false);

                loading.setVisible(false);
                title_a.setBackground(new Color(220, 205, 223));

            }

            @Override
            public void selectUser(Model_User_Account user) {
                home.setUser(user);
            }

            @Override
            public void updateUser(Model_User_Account user) {
                home.updateUser(user);
            }

            @Override
            public void updateProfile(Model_Profile dataPr) {
                home.setModelProfile(dataPr);
            }

            @Override
            public void setTitleName(String s) {
                if (s != null && !s.isEmpty()) {
                    lbTitleName.setText("-" + s);
                }
            }

            @Override
            public Home getHome() {
                return home;
            }

            @Override
            public String processImage(byte[] imageBytes) throws IOException{
                // Đọc ảnh từ mảng byte
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                BufferedImage originalImage = ImageIO.read(inputStream);

                // Thay đổi kích thước và nén ảnh
                BufferedImage resizedImage = Thumbnails.of(originalImage)
                        .size(70, 50) // Thay đổi kích thước
                        .outputQuality(0.5) // Chất lượng nén
                        .asBufferedImage();

                // Ghi ảnh ra ByteArrayOutputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "jpg", outputStream);

                // Chuyển đổi ByteArrayOutputStream thành chuỗi Base64
                byte[] compressedImageBytes = outputStream.toByteArray();
                String base64Image = Base64.getEncoder().encodeToString(compressedImageBytes);

                return base64Image;
            }

            

        });
        PublicEvent.getInstance().addEventProfile(new EventProfile() {

            @Override
            public void viewProfile() {
                view_Profile.viewProfile();
            }

            @Override
            public Icon createImage(String imageBase64) {
                if (imageBase64 == null || imageBase64.isEmpty()) {
                    // Xử lý trường hợp không có dữ liệu hình ảnh
                    return null;
                }
                try {
                    // Chuyển từ chuỗi Base64 thành mảng byte
                    byte[] decodedImageBytes = Base64.getDecoder().decode(imageBase64);

                    // Tạo một ImageIcon từ mảng byte
                    ImageIcon imageIcon = new ImageIcon(decodedImageBytes);
                    return imageIcon;
                } catch (IllegalArgumentException e) {
                    // Xử lý nếu có lỗi trong quá trình giải mã Base64
                    e.printStackTrace(); // Hoặc thực hiện xử lý lỗi khác phù hợp
                    return null;
                }

            }

            @Override
            public CompletableFuture<Model_Profile> getProfileAsync(Model_User_Account data) {
                CompletableFuture<Model_Profile> future = new CompletableFuture<>();

                Service.getInstance().getClient().emit("get_info", data.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {
                            Model_Profile dataPro = new Model_Profile(os[0]);
                            System.out.println("Nhận profile thành công");
                            future.complete(dataPro);
                        } else {
                            System.out.println("Không nhận được thông tin profile");
                            future.completeExceptionally(new Exception("Không nhận được thông tin profile"));
                        }
                    }
                });

                return future;
            }

            @Override
            public void updateProfile(Model_Profile_Update dataPr) {
                Service.getInstance().getClient().emit("update_profile_profile", dataPr.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {
                            System.out.println("Da update profile");
                        } else {
                            System.out.println("Ko the update");
                        }
                    }
                });

            }

            @Override
            public boolean updateAavatar(Model_Image_Update dataImage) {
                CompletableFuture<Boolean> future = new CompletableFuture<>();
                List<Model_Image_Update> chunks = createChunks(dataImage.getUserID(), dataImage.getImageData());

                for (Model_Image_Update chunk : chunks) {
                    Service.getInstance().getClient().emit("update_avatar", chunk.toJsonObject(), new Ack() {
                        @Override
                        public void call(Object... os) {
                            if (os.length > 0 && (boolean) os[0]) {
                                System.out.println("Da update profile chunk");
                            } else {
                                System.out.println("Ko the update chunk");
                                future.complete(false);
                            }
                        }
                    });
                }

                future.complete(true);
                return future.join();
            }

            @Override
            public void updateName(Model_Name_Update name) {

                Service.getInstance().getClient().emit("update_name_profile", name.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {
                            System.out.println("Da update profile");
                        } else {
                            System.out.println("Ko the update");
                        }
                    }
                });
            }

            public List<Model_Image_Update> createChunks(int userID, String imageData) {
                int chunkSize = 30000; // Kích thước mỗi chunk
                List<Model_Image_Update> chunks = new ArrayList<>();
                for (int i = 0; i < imageData.length(); i += chunkSize) {
                    String chunk = imageData.substring(i, Math.min(imageData.length(), i + chunkSize));
                    boolean isLastChunk = (i + chunkSize) >= imageData.length();
                    chunks.add(new Model_Image_Update(userID, chunk, isLastChunk));
                }
                return chunks;
            }

            @Override
            public boolean updateCoverArt(Model_Image_Update dataImage) {
                CompletableFuture<Boolean> future = new CompletableFuture<>();
                List<Model_Image_Update> chunks = createChunks(dataImage.getUserID(), dataImage.getImageData());

                for (Model_Image_Update chunk : chunks) {
                    Service.getInstance().getClient().emit("update_coverart", chunk.toJsonObject(), new Ack() {
                        @Override
                        public void call(Object... os) {
                            if (os.length > 0 && (boolean) os[0]) {
                                System.out.println("Da update profile chunk");
                            } else {
                                System.out.println("Ko the update chunk");
                                future.complete(false);
                            }
                        }
                    });
                }

                future.complete(true);
                return future.join();
            }
            

            @Override
            public CompletableFuture<String> getCoverArt(UserIDToJSON userID) {
                CompletableFuture<String> future = new CompletableFuture<>();
                StringBuilder receivedDataBuilder = new StringBuilder();

                Service.getInstance().getClient().on("get_cover_chunk", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        if (args.length > 0) {
                            Model_Image_Update d = new Model_Image_Update(args[0]);
                            String chunkData = d.getImageData();
                            boolean isLastChunk = d.isLastChunk();

                            // Ghép chunk vào StringBuilder
                            receivedDataBuilder.append(chunkData);

                            if (isLastChunk) {
                                // Nếu là chunk cuối, hoàn thành CompletableFuture
                                future.complete(receivedDataBuilder.toString());
                            }
                        } else {
                            future.completeExceptionally(new Exception("Invalid chunk received"));
                        }
                    }
                });//

                // Gửi yêu cầu lấy ảnh đại diện
                Service.getInstance().getClient().emit("get_image_cover", userID.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... args) {
                        if (args.length == 0) {
                            future.completeExceptionally(new Exception("No response from server"));
                        }
                    }
                });
                return future;
            }

            @Override
            public CompletableFuture<String> getAvt(UserIDToJSON userID) {
                CompletableFuture<String> future = new CompletableFuture<>();
                StringBuilder receivedDataBuilder = new StringBuilder();

                Service.getInstance().getClient().on("image_avt_chunk", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        if (args.length > 0) {
                            Model_Image_Update d = new Model_Image_Update(args[0]);
                            String chunkData = d.getImageData();
                            boolean isLastChunk = d.isLastChunk();

                            // Ghép chunk vào StringBuilder
                            receivedDataBuilder.append(chunkData);

                            if (isLastChunk) {
                                // Nếu là chunk cuối, hoàn thành CompletableFuture
                                future.complete(receivedDataBuilder.toString());
                            }
                        } else {
                            future.completeExceptionally(new Exception("Invalid chunk received"));
                        }
                    }
                });//get_image_cover

                // Gửi yêu cầu lấy ảnh đại diện
                Service.getInstance().getClient().emit("get_image_avt", userID.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... args) {
                        if (args.length == 0) {
                            future.completeExceptionally(new Exception("No response from server"));
                        }
                    }
                });

                return future;
            }

        });
        PublicEvent.getInstance().addEventImageView(new EventImageView() {
            @Override
            public void viewImage(Icon image, String mode, int fileID, String fileExcetion, String fileName) {
                vIew_Image.viewImage(image, mode, fileID, fileExcetion, fileName);
            }

            @Override
            public void saveImage(int fileId, String fileExcetion, String fileName) {
                String filePath = "client_data/" + fileId + fileExcetion;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
                String defaultFileName = fileName;
                fileChooser.setSelectedFile(new File(defaultFileName));
                int result = fileChooser.showSaveDialog(Main.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFile = getUniqueFileName(selectedFile);
                    try {
                        Files.copy(new File(filePath).toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File saved successfully: " + selectedFile.getAbsolutePath());
                    } catch (IOException ex) {
                        System.err.println("Error saving file: " + ex.getMessage());
                    }
                }
            }

            @Override
            public void viewLocation(String mode, int fileId, String fileExcetion, String fileName) {
                if (mode.equals("sender")) {
                    String filePath = fileName;
                    System.out.println("FilePath=" + filePath);
                    File file = new File(filePath).getAbsoluteFile();
                    String absolutePath = file.getAbsolutePath();
                    String command = "explorer.exe /select," + absolutePath;
                    try {    // Chạy lệnh
                        Process process = Runtime.getRuntime().exec(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String filePath = "client_data/";
                    filePath +=fileId + fileExcetion;
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
            public void downFile(int fileID, String fileExcetion, String fileName) {
                String filePath = "client_data/" + fileID + fileExcetion;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);

                // Lấy tên file từ model và đặt làm tên file mặc định
                String defaultFileName = fileName; // Đổi thành tên file từ model của bạn

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
            public void localFileReceiver(int fileID, String fileExcetion) {
                String filePath = "client_data/";
                filePath += fileID+ fileExcetion;
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
            public void localFileSender(int fileID, String fileExcetion, String fileP) {

                String filePath = fileP;
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
        view_Profile = new com.raven.form.View_Profile();
        vIew_Image = new com.raven.form.VIew_Image();
        home = new com.raven.form.Home();
        title_a = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        title_m = new javax.swing.JPanel();
        cmdClose = new com.raven.component.OptionButton();
        cmdMaximize = new com.raven.component.OptionButton();
        cmdMinimize = new com.raven.component.OptionButton();
        lbTitleName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        border.setBackground(new java.awt.Color(229, 229, 229));

        background.setBackground(new java.awt.Color(255, 255, 255));

        body.setLayout(new java.awt.CardLayout());
        body.add(loading, "card5");
        body.add(login, "card4");
        body.add(view_Profile, "card6");
        body.setLayer(vIew_Image, javax.swing.JLayeredPane.POPUP_LAYER);
        body.add(vIew_Image, "card3");
        body.add(home, "card2");

        title_a.setBackground(new java.awt.Color(219, 232, 249));
        title_a.setMinimumSize(new java.awt.Dimension(50, 0));
        title_a.setPreferredSize(new java.awt.Dimension(50, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chat App");
        jLabel1.setPreferredSize(new java.awt.Dimension(50, 20));

        javax.swing.GroupLayout title_aLayout = new javax.swing.GroupLayout(title_a);
        title_a.setLayout(title_aLayout);
        title_aLayout.setHorizontalGroup(
            title_aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, title_aLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        title_aLayout.setVerticalGroup(
            title_aLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        lbTitleName.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        lbTitleName.setForeground(new java.awt.Color(0, 102, 153));

        javax.swing.GroupLayout title_mLayout = new javax.swing.GroupLayout(title_m);
        title_m.setLayout(title_mLayout);
        title_mLayout.setHorizontalGroup(
            title_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, title_mLayout.createSequentialGroup()
                .addComponent(lbTitleName, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        title_mLayout.setVerticalGroup(
            title_mLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmdMaximize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmdMinimize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTitleName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
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
                    .addComponent(title_a, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbTitleName;
    private com.raven.form.Loading loading;
    private com.raven.form.Login login;
    private javax.swing.JPanel title_a;
    private javax.swing.JPanel title_m;
    private com.raven.form.VIew_Image vIew_Image;
    private com.raven.form.View_Profile view_Profile;
    // End of variables declaration//GEN-END:variables
}
