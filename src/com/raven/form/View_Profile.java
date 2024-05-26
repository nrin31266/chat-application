package com.raven.form;

import com.raven.event.EventViewProfile;
import com.raven.event.PublicEvent;
import com.raven.model.Model_Profile;
import com.raven.swing.ScrollBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import javax.swing.JLabel;

public class View_Profile extends javax.swing.JPanel {
    private Model_Profile modelProfileMe;

    public Model_Profile getModelProfileMain() {
        return modelProfileMe;
    }

    public void setModelProfile(Model_Profile modelProfileMe) {
        this.modelProfileMe = modelProfileMe;
    }
    
    
    public View_Profile() {
        initComponents();
        scroll.setVerticalScrollBar(new ScrollBar());
        setOpaque(false);
        stackAvatarOnCoverArt();
        info2.setVisible(true);
        info1.setVisible(true);
        info1.add(new Label("1"));
        info2.add(new Label("2"));
        PublicEvent.getInstance().addEventViewProfile(new EventViewProfile(){
            @Override
            public void setProfileMe(Model_Profile dataPr) {
                modelProfileMe=dataPr;
                if(dataPr.getImage()!=null&&!dataPr.getImage().isEmpty()){
                    avt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getImage()));
                    avt.repaint();
                }
                if(dataPr.getCoverArt()!=null&&!dataPr.getCoverArt().isEmpty()){
                    coverArt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getCoverArt()));
                    coverArt.repaint();
                }
                
                
            }
        });
    }
    
    public void stackAvatarOnCoverArt() {
        int coverArtWidth = 350;
        int coverArtHeight = 150;
        int avatarWidth = 100;
        int avatarHeight = 100;
        int avatarX = 10;
        int avatarY = coverArtHeight - avatarHeight / 2; // Đặt nửa dưới của avt trên coverArt

        // Đặt avt ở giữa phía dưới của pictureCoverArt1
        MenuList.add(avt, javax.swing.JLayeredPane.PALETTE_LAYER);
        avt.setBounds(avatarX, avatarY, avatarWidth, avatarHeight);
    }

    public void viewProfile() {
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();
        title = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmdClose = new com.raven.component.OptionButton();
        scroll = new javax.swing.JScrollPane();
        MenuList = new javax.swing.JLayeredPane();
        coverArt = new com.raven.swing.PictureCoverArt();
        avt = new com.raven.swing.ImageAvatar();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        information = new javax.swing.JLayeredPane();
        info1 = new javax.swing.JPanel();
        info2 = new javax.swing.JPanel();
        title1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmdEditProfile = new com.raven.component.OptionButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        body.setBackground(new java.awt.Color(255, 255, 255));

        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setPreferredSize(new java.awt.Dimension(82, 25));
        title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleMousePressed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText(" Thông tin tài khoản:");

        cmdClose.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/close_panel.png"))); // NOI18N
        cmdClose.setPreferredSize(new java.awt.Dimension(25, 25));
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout titleLayout = new javax.swing.GroupLayout(title);
        title.setLayout(titleLayout);
        titleLayout.setHorizontalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        titleLayout.setVerticalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        scroll.setForeground(new java.awt.Color(255, 255, 255));
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        MenuList.setBackground(new java.awt.Color(255, 255, 255));
        MenuList.setForeground(new java.awt.Color(255, 255, 255));
        MenuList.setOpaque(true);

        coverArt.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/testing/dog.jpg"))); // NOI18N

        avt.setBorderColor(new java.awt.Color(255, 255, 255));
        avt.setBorderSize(4);
        avt.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        avt.setPreferredSize(new java.awt.Dimension(100, 100));
        coverArt.add(avt);
        avt.setBounds(0, 100, 100, 100);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        information.setLayout(new java.awt.CardLayout());

        info1.setLayout(new javax.swing.BoxLayout(info1, javax.swing.BoxLayout.Y_AXIS));
        information.add(info1, "card2");

        info2.setLayout(new javax.swing.BoxLayout(info2, javax.swing.BoxLayout.Y_AXIS));
        information.add(info2, "card3");

        title1.setBackground(new java.awt.Color(255, 255, 255));
        title1.setPreferredSize(new java.awt.Dimension(82, 25));
        title1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                title1MousePressed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText(" Thông tin cá nhân:");

        cmdEditProfile.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/edit_profile.png"))); // NOI18N
        cmdEditProfile.setPreferredSize(new java.awt.Dimension(25, 25));
        cmdEditProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout title1Layout = new javax.swing.GroupLayout(title1);
        title1.setLayout(title1Layout);
        title1Layout.setHorizontalGroup(
            title1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(title1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
                .addComponent(cmdEditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        title1Layout.setVerticalGroup(
            title1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdEditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MenuList.setLayer(coverArt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(information, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(title1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout MenuListLayout = new javax.swing.GroupLayout(MenuList);
        MenuList.setLayout(MenuListLayout);
        MenuListLayout.setHorizontalGroup(
            MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuListLayout.createSequentialGroup()
                .addGroup(MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(information, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MenuListLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(coverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(203, 203, 203))
            .addGroup(MenuListLayout.createSequentialGroup()
                .addComponent(title1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuListLayout.setVerticalGroup(
            MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuListLayout.createSequentialGroup()
                .addComponent(coverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(title1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(information, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        scroll.setViewportView(MenuList);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 544, Short.MAX_VALUE)
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                    .addGap(0, 29, Short.MAX_VALUE)
                    .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bodyLayout.createSequentialGroup()
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(519, 519, 519)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cmdCloseActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        setVisible(false);
    }//GEN-LAST:event_formMousePressed

    private void titleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleMousePressed
        
    }//GEN-LAST:event_titleMousePressed

    private void cmdEditProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditProfileActionPerformed
        if(info1.isVisible()){
            info1.setVisible(false);
            info2.setVisible(true);  
            
        }else{
            info2.setVisible(false);
            info1.setVisible(true);
            
        }
    }//GEN-LAST:event_cmdEditProfileActionPerformed

    private void title1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_title1MousePressed
    

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane MenuList;
    private com.raven.swing.ImageAvatar avt;
    private javax.swing.JPanel body;
    private com.raven.component.OptionButton cmdClose;
    private com.raven.component.OptionButton cmdEditProfile;
    private com.raven.swing.PictureCoverArt coverArt;
    private javax.swing.JPanel info1;
    private javax.swing.JPanel info2;
    private javax.swing.JLayeredPane information;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JPanel title;
    private javax.swing.JPanel title1;
    // End of variables declaration//GEN-END:variables
}
