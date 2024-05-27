package com.raven.form;

import com.raven.event.PublicEvent;
import com.raven.model.Model_Profile;
import com.raven.service.Service;
public class Menu extends javax.swing.JPanel {

    private Model_Profile modelProfile;
    
    
    public Model_Profile getModelProfile() {
        return modelProfile;
    }

    public void setModelProfile(Model_Profile modelProfile) {
        this.modelProfile = modelProfile;
        if(modelProfile.getImage()!=null&&!modelProfile.getImage().isEmpty()){
            imageAvatar.setImage(PublicEvent.getInstance().getEventProfile().createImage(modelProfile.getImage()));
            imageAvatar.repaint();
        }
    }
    
    public Menu() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageAvatar = new com.raven.swing.ImageAvatar();

        setBackground(new java.awt.Color(220, 205, 223));
        setMaximumSize(new java.awt.Dimension(50, 32767));
        setMinimumSize(new java.awt.Dimension(50, 0));

        imageAvatar.setBorderSize(0);
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        imageAvatar.setMaximumSize(new java.awt.Dimension(44, 44));
        imageAvatar.setMinimumSize(new java.awt.Dimension(44, 44));
        imageAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageAvatarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(588, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void imageAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageAvatarMouseClicked
        if(modelProfile==null){
            modelProfile= new Model_Profile();
            modelProfile.setUserID(Service.getInstance().getUser().getUserID());
        }
        PublicEvent.getInstance().getEventViewProfile().setProfile(modelProfile, 1);
        PublicEvent.getInstance().getEventProfile().viewProfile();
    }//GEN-LAST:event_imageAvatarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.ImageAvatar imageAvatar;
    // End of variables declaration//GEN-END:variables
}
