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
        imageAvatar = new com.raven.swing.ImageAvatar();
        cmdProfile = new com.raven.component.OptionButton();

        setBackground(new java.awt.Color(204, 204, 204));

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane1.setOpaque(true);

        lbName.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        lbName.setForeground(new java.awt.Color(66, 66, 66));
        lbName.setText("Name");

        lbStatus.setForeground(new java.awt.Color(40, 147, 59));
        lbStatus.setText("Active now");

        imageAvatar.setForeground(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderColor(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderSize(0);
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        imageAvatar.setPreferredSize(new java.awt.Dimension(38, 38));

        javax.swing.GroupLayout imageAvatarLayout = new javax.swing.GroupLayout(imageAvatar);
        imageAvatar.setLayout(imageAvatarLayout);
        imageAvatarLayout.setHorizontalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        imageAvatarLayout.setVerticalGroup(
            imageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        cmdProfile.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/more_account.png"))); // NOI18N
        cmdProfile.setPreferredSize(new java.awt.Dimension(30, 30));
        cmdProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmdProfileMouseClicked(evt);
            }
        });
        cmdProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdProfileActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayer(lbName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(lbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(imageAvatar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cmdProfile, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(cmdProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(88, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jLayeredPane1Layout.createSequentialGroup()
                        .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(27, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdProfileMouseClicked
        PublicEvent.getInstance().getEventMain().showLoading(true);

        CompletableFuture<Model_Profile> profileFuture = PublicEvent.getInstance().getEventProfile().getProfileAsync(user);
        Model_Profile profile = profileFuture.join();

        CompletableFuture<String> avatarFuture = PublicEvent.getInstance().getEventProfile().getAvt(new UserIDToJSON(user.getUserID()));

        String avatar = null;

        try {
            avatar = avatarFuture.join();
            profile.setImage(avatar);
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof Exception) {
                Exception cause = (Exception) ex.getCause();
                if (cause.getMessage().equals("No response from server")) {
                    profile.setImage("");
                } else {
                    profile.setImage("");
                }
            } else {
                profile.setImage("");
            }
        }
        CompletableFuture<String> coverArtFuture = PublicEvent.getInstance().getEventProfile().getCoverArt(new UserIDToJSON(user.getUserID()));
        String cover = null;
        try {
            cover = coverArtFuture.join();
            profile.setCoverArt(cover);
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof Exception) {
                Exception cause = (Exception) ex.getCause();
                if (cause.getMessage().equals("No response from server")) {
                    
                } else {
                    
                }
            } else {
                profile.setCoverArt("");
            }
        }

        PublicEvent.getInstance().getEventViewProfile().setProfile(profile, 2);
        PublicEvent.getInstance().getEventProfile().viewProfile();
        PublicEvent.getInstance().getEventMain().showLoading(false);

    }//GEN-LAST:event_cmdProfileMouseClicked

    private void cmdProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdProfileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdProfile;
    private com.raven.swing.ImageAvatar imageAvatar;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
