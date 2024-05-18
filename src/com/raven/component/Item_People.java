package com.raven.component;

import com.raven.event.PublicEvent;
import com.raven.model.Model_User_Account;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Item_People extends javax.swing.JPanel {

    private final Model_User_Account user;
    private boolean mouseOver;    
    private static Item_People selectedPanel = null;
    private Color defaultColor = new Color(242, 242, 242);
    private Color hoverColor = new Color(230, 230, 230);
    private Color selectedColor = new Color(209, 243, 255);
    
    public Item_People(Model_User_Account user) {
        this.user=user;
        initComponents();
        lbName.setText(user.getUserName());
        activeStatus.setActive(user.isStatus());
        init();
    }
    public  void updateStatus(){
        activeStatus.setActive(user.isStatus());
    }
    
    public Model_User_Account getUser() {
        return user;
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if (Item_People.this != selectedPanel) {
                    setBackground(hoverColor);
                }
                mouseOver=true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (Item_People.this != selectedPanel) {
                    setBackground(defaultColor);
                }
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(mouseOver){
                    System.out.println("Item people Press: "+user.getUserID());
                    
                    PublicEvent.getInstance().getEventMain().selectUser(user);
                    
                    if (selectedPanel != null) {
                        selectedPanel.setBackground(defaultColor);
                    }
                    setBackground(selectedColor);
                    selectedPanel = Item_People.this;
                }
            }
            
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageAvatar1 = new com.raven.swing.ImageAvatar();
        lbName = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        activeStatus = new com.raven.swing.ActiveStatus();

        setBackground(new java.awt.Color(242, 241, 242));
        setPreferredSize(new java.awt.Dimension(200, 62));

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        imageAvatar1.setPreferredSize(new java.awt.Dimension(50, 50));

        lbName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbName.setText("Name");

        lbStatus.setBackground(new java.awt.Color(255, 255, 255));
        lbStatus.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(153, 153, 153));
        lbStatus.setText("New");

        activeStatus.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(activeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(102, 102, 102))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(activeStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lbStatus))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.ActiveStatus activeStatus;
    private com.raven.swing.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
