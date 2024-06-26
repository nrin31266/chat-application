package com.raven.form;

import com.raven.event.EventLogin;
import com.raven.event.EventMessage;
import com.raven.event.PublicEvent;
import com.raven.model.Model_Login;
import com.raven.model.Model_Message;
import com.raven.model.Model_Profile;
import com.raven.model.Model_Register;
import com.raven.model.Model_User_Account;
import com.raven.model.UserIDToJSON;
import com.raven.service.Service;
import io.socket.client.Ack;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.attribute.standard.Severity;

public class Login extends javax.swing.JPanel {

    public Login() {
        initComponents();
        init();
    }

    private void init() {
        PublicEvent.getInstance().addEventLogin(new EventLogin() {
            @Override
            public void login(Model_Login data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PublicEvent.getInstance().getEventMain().showLoading(true);
                        Service.getInstance().getClient().emit("login", data.toJsonObject(), new Ack() {
                            @Override
                            public void call(Object... os) {
                                try {
                                    Thread.sleep(0);
                                    System.err.println("Da nhan dc phan hoi tu server");
                                    if (os.length > 0) {
                                        boolean action = (Boolean) os[0];

                                        if (action) {
                                            Model_User_Account user = new Model_User_Account(os[1]);
                                            Service.getInstance().setUser(user);
                                            CompletableFuture<Model_Profile> profileFuture = PublicEvent.getInstance().getEventProfile().getProfileAsync(user);

                                            profileFuture.thenCompose(profile -> {
                                                CompletableFuture<String> avatarFuture = PublicEvent.getInstance().getEventProfile().getAvt(new UserIDToJSON(user.getUserID()));
                                                return avatarFuture.handle((avatar, ex) -> {
                                                    if (ex != null) {
                                                        avatar = "";
                                                    }
                                                    profile.setImage(avatar);
                                                    return profile;
                                                });
                                            }).thenCompose(profile -> {
                                                CompletableFuture<String> coverArtFuture = PublicEvent.getInstance().getEventProfile().getCoverArt(new UserIDToJSON(user.getUserID()));
                                                return coverArtFuture.handle((cover, ex) -> {
                                                    if (ex != null) {
                                                        cover = "";
                                                    }
                                                    profile.setCoverArt(cover);
                                                    return profile;
                                                });
                                            }).thenAccept(profile -> {
                                                PublicEvent.getInstance().getEventMain().setTitleName(profile.getName());
                                                PublicEvent.getInstance().getEventMain().getHome().setModelProfile(profile);
                                                PublicEvent.getInstance().getEventMain().initChat();
//                                                PublicEvent.getInstance().getEventMain().showLoading(false);
                                            }).exceptionally(ex -> {
                                                PublicEvent.getInstance().getEventMain().showLoading(false);
                                                System.err.println("Error: " + ex.getMessage());
                                                return null;
                                            });
                                        } else {
                                            // Password wrong
                                            PublicEvent.getInstance().getEventMain().showLoading(false);
                                        }
                                    } else {
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                        System.err.println("Ko nhan dc phan hoi tu server");
                                    }
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void register(Model_Register data, EventMessage message) {
                PublicEvent.getInstance().getEventMain().showLoading(true);
                Service.getInstance().getClient().emit("register", data.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {

                            Model_Message ms = new Model_Message((boolean) os[0], os[1].toString());

                            if (ms.isAction()) {
                                Model_User_Account user = new Model_User_Account(os[2]);
                                Service.getInstance().setUser(user);
                            }
                            message.callMessage(ms);
                            //  call message back when done register
                        }
                    }
                });
            }

            @Override
            public void goRegister() {
                slide.show(1);
            }

            @Override
            public void goLogin() {
                slide.show(0);
            }

            @Override
            public String transformPassword(String password, int mode) {
                // Sử dụng khóa cố định cho quá trình mã hóa/giải mã
                String key = "simplekey";

                byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
                byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                byte[] result = new byte[passwordBytes.length];

                for (int i = 0; i < passwordBytes.length; i++) {
                    result[i] = (byte) (passwordBytes[i] ^ keyBytes[i % keyBytes.length]);
                }

                // Trả về kết quả dưới dạng chuỗi
                return new String(result, StandardCharsets.UTF_8);
            }
        });
        P_Login login = new P_Login();
        P_Register register = new P_Register();
        slide.init(login, register);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.raven.swing.PictureBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        slide = new com.raven.swing.PanelSlide();

        setBackground(new java.awt.Color(111, 196, 251));

        pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/login_image.jpg")));

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(32, 140, 215));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        slide.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 318, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 100, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.raven.swing.PictureBox pic;
    private com.raven.swing.PanelSlide slide;
    // End of variables declaration//GEN-END:variables
}
