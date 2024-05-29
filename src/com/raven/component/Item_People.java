package com.raven.component;

import com.raven.event.PublicEvent;
import com.raven.model.Model_Get_Chat_History;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.ImageAvatar;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;

public class Item_People extends javax.swing.JPanel {

    public Model_User_Account getUser() {
        return user;
    }

    private boolean mouseOver;
    private final Model_User_Account user;

    public Item_People(Model_User_Account user) {
        this.user = user;

        initComponents();

        lbName.setText(user.getUserName());
        activeStatus.setActive(user.isStatus());
        if (user.getImage() != null && !user.getImage().equals("")) {
            imageAvatar.setImage(PublicEvent.getInstance().getEventProfile().createImage(user.getImage()));
            imageAvatar.repaint();
        }
        init();

    }

    public void updateStatus() {
        activeStatus.setActive(user.isStatus());
        repaint();
    }

    public void setAvatar(String image) {
        Icon icon = PublicEvent.getInstance().getEventProfile().createImage(image);
        imageAvatar.setImage(icon);
        imageAvatar.repaint();
        imageAvatar.revalidate();
    }

    private void init() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(230, 230, 230));
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(Color.white);
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {

                if (mouseOver) {
                   
                    
                    if (Service.getInstance().getSelectedUser() != null) {

                        if (Service.getInstance().getSelectedUser().getUserID() != user.getUserID()) {
                            Service.getInstance().setSelectedUser(user);
                            Model_Get_Chat_History d = new Model_Get_Chat_History(Service.getInstance().getUser().getUserID(), user.getUserID());
                            PublicEvent.getInstance().getEventMain().selectUser(user);
                            PublicEvent.getInstance().getEventBody().receiverChatHisTory(d);
                        }
                    } else {
                        Service.getInstance().setSelectedUser(user);
                        Model_Get_Chat_History d = new Model_Get_Chat_History(Service.getInstance().getUser().getUserID(), user.getUserID());
                        PublicEvent.getInstance().getEventMain().selectUser(user);
                        PublicEvent.getInstance().getEventBody().receiverChatHisTory(d);
                    }
                    
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageAvatar = new com.raven.swing.ImageAvatar();
        lbName = new javax.swing.JLabel();
        activeStatus = new com.raven.swing.ActiveStatus();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(200, 50));

        imageAvatar.setForeground(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderColor(new java.awt.Color(255, 255, 255));
        imageAvatar.setBorderSize(2);
        imageAvatar.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        imageAvatar.setMaximumSize(new java.awt.Dimension(60, 60));
        imageAvatar.setMinimumSize(new java.awt.Dimension(60, 60));
        imageAvatar.setPreferredSize(new java.awt.Dimension(60, 60));

        lbName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbName.setText("Name");

        activeStatus.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imageAvatar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(activeStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.ActiveStatus activeStatus;
    private com.raven.swing.ImageAvatar imageAvatar;
    private javax.swing.JLabel lbName;
    // End of variables declaration//GEN-END:variables
}
