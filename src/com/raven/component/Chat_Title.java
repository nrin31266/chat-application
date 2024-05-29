package com.raven.component;

import com.raven.event.PublicEvent;
import com.raven.model.Model_Profile;
import com.raven.model.Model_User_Account;
import com.raven.model.UserIDToJSON;
import java.awt.Color;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.swing.ImageIcon;

public class Chat_Title extends javax.swing.JPanel {

    private Model_User_Account user;
    private final String pathAvatarBasic = "/com/raven/icon/profile.png";

    public Model_User_Account getUser() {
        return user;
    }

    public Chat_Title() {
        initComponents();
    }

    public void refreshUser() {
        imageAvatar.setImage(new ImageIcon(getClass().getResource(pathAvatarBasic)));
        imageAvatar.repaint();
    }

    public void setUserName(Model_User_Account user) {
        this.user = user;
        refreshUser();
        ///
        lbName.setText(user.getUserName());
        if (user.isStatus()) {
            statusActive();
        } else {
            setStatusText("Ngoại tuyến");
        }
        if (user.getImage() != null && !user.getImage().equals("")) {
            imageAvatar.setImage(PublicEvent.getInstance().getEventProfile().createImage(user.getImage()));
            imageAvatar.repaint();
        }
    }

    public void updateUser(Model_User_Account user) {
        if (this.user == user) {
            lbName.setText(user.getUserName());
            if (user.isStatus()) {
                statusActive();
            } else {
                setStatusText("Ngoại tuyến");
            }
        }
    }

    private void statusActive() {
        lbStatus.setText("Trực tuyến");
        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
    }

    private void setStatusText(String text) {
        lbStatus.setText(text);
        lbStatus.setForeground(new Color(160, 160, 160));
    }
    private Model_Profile modelProfile;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        lbName = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        cmdProfile = new com.raven.component.OptionButton();
        imageAvatar = new com.raven.swing.ImageAvatar();

        setBackground(new java.awt.Color(204, 204, 204));

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane1.setOpaque(true);
        jLayeredPane1.setPreferredSize(new java.awt.Dimension(350, 51));

        lbName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbName.setForeground(new java.awt.Color(66, 66, 66));
        lbName.setText("Name");

        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
        lbStatus.setText("Active now");

        cmdProfile.setBackground(new java.awt.Color(218, 218, 218));
        cmdProfile.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/more_account.png"))); // NOI18N
        cmdProfile.setPreferredSize(new java.awt.Dimension(30, 30));
        cmdProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdProfileMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdProfileMouseExited(evt);
            }
        });
        cmdProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdProfileActionPerformed(evt);
            }
        });

        imageAvatar.setForeground(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderColor(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderSize(2);
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        imageAvatar.setMaximumSize(new java.awt.Dimension(50, 50));
        imageAvatar.setMinimumSize(new java.awt.Dimension(50, 50));

        javax.swing.GroupLayout imageAvatarLayout = new javax.swing.GroupLayout(imageAvatar);
        imageAvatar.setLayout(imageAvatarLayout);
        imageAvatarLayout.setHorizontalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        imageAvatarLayout.setVerticalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jLayeredPane1.setLayer(lbName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cmdProfile, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(imageAvatar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdProfileMouseClicked
        // Hiển thị loading
        PublicEvent.getInstance().getEventMain().showLoading(true);

// Lấy các CompletableFuture
        CompletableFuture<Model_Profile> profileFuture = PublicEvent.getInstance().getEventProfile().getProfileAsync(user);
        CompletableFuture<String> avatarFuture = PublicEvent.getInstance().getEventProfile().getAvt(new UserIDToJSON(user.getUserID()))
                .handle((result, ex) -> {
                    if (ex != null) {
                        // Xử lý lỗi cho avatarFuture
                        return "";
                    }
                    return result;
                });
        CompletableFuture<String> coverArtFuture = PublicEvent.getInstance().getEventProfile().getCoverArt(new UserIDToJSON(user.getUserID()))
                .handle((result, ex) -> {
                    if (ex != null) {
                        // Xử lý lỗi cho coverArtFuture
                        return "";
                    }
                    return result;
                });

// Sử dụng CompletableFuture.allOf để đợi tất cả các future hoàn thành
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(profileFuture, avatarFuture, coverArtFuture);

        allFutures.thenRun(() -> {
            try {
                Model_Profile profile = profileFuture.join();
                String avatar = avatarFuture.join();
                String cover = coverArtFuture.join();

                // Thiết lập ảnh đại diện và ảnh bìa
                profile.setImage(avatar);
                profile.setCoverArt(cover);

                // Thiết lập profile và hiển thị
                PublicEvent.getInstance().getEventViewProfile().setProfile(profile, 2);
                PublicEvent.getInstance().getEventProfile().viewProfile();
            } catch (CompletionException ex) {
                // Xử lý ngoại lệ nếu cần
                ex.printStackTrace();
            } finally {
                // Ẩn loading
                PublicEvent.getInstance().getEventMain().showLoading(false);
            }
        });

    }//GEN-LAST:event_cmdProfileMouseClicked

    private void cmdProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdProfileActionPerformed

    private void cmdProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdProfileMouseEntered
        cmdProfile.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdProfileMouseEntered

    private void cmdProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdProfileMouseExited
        cmdProfile.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdProfileMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdProfile;
    private com.raven.swing.ImageAvatar imageAvatar;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
