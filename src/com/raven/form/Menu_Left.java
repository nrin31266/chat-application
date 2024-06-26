package com.raven.form;

import com.raven.component.Item_People;
import com.raven.event.EventMenuLeft;
import com.raven.event.PublicEvent;
import com.raven.model.Model_Profile;
import com.raven.model.Model_User_Account;
import com.raven.swing.ScrollBar;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class Menu_Left extends javax.swing.JPanel {

    private List<Model_User_Account> userAccount;

    private Model_Profile model_profile;

    public Model_Profile getModel_profile() {
        return model_profile;
    }

    public void setModel_profile(Model_Profile model_profile) {
        this.model_profile = model_profile;
    }

    public Menu_Left() {
        initComponents();
        init();
    }

    private void init() {
        sp.setVerticalScrollBar(new ScrollBar());
        menuList.setLayout(new MigLayout("fillx", "0[]0", "0[]0"));
        userAccount = new ArrayList<>();

        /////
        PublicEvent.getInstance().addEventMenuLeft(new EventMenuLeft() {
            @Override
            public void newUser(List<Model_User_Account> users) {
//                for (Model_User_Account d : users) {
//                    userAccount.add(d);
//                    menuList.add(new Item_People(d), "wrap");
//                    refreshMenuList();
//                }
//                
                if (!users.isEmpty()) {
                    Model_User_Account firstUser = users.get(0);
                    Item_People item = new Item_People(firstUser);
                    menuList.add(item, "wrap");
                    userAccount.add(firstUser);
                    // Gọi phương thức performMouseReleasedAction() thay vì mouseReleased()
                    item.performMouseReleasedAction();
                }
                for (int i = 1; i < users.size(); i++) {
                    Model_User_Account user = users.get(i);
                    userAccount.add(user);
                    menuList.add(new Item_People(user), "wrap");
                }
            }

            @Override
            public void userConnect(int userID) {
                for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == userID) {
                        u.setStatus(true);
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                if (menuMessage.isSelected()) {
                    for (Component com : menuList.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == userID) {
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }

            @Override
            public void userDisconnect(int userID) {
                for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == userID) {
                        u.setStatus(false);
                        PublicEvent.getInstance().getEventMain().updateUser(u);
                        break;
                    }
                }
                if (menuMessage.isSelected()) {
                    for (Component com : menuList.getComponents()) {
                        Item_People item = (Item_People) com;
                        if (item.getUser().getUserID() == userID) {
                            item.updateStatus();
                            break;
                        }
                    }
                }
            }

            @Override
            public void updateAvatar(int userID, String dataImage) {
                for (Model_User_Account u : userAccount) {
                    if (u.getUserID() == userID) {
                        u.setImage(dataImage);
                        break;
                    }
                }
                for (Component com : menuList.getComponents()) {
                    Item_People item = (Item_People) com;
                    if (item.getUser().getUserID() == userID) {
                        item.setAvatar(dataImage);
                        break;
                    }
                }
            }
        });
        showMessage();
    }

    private void showMessage() {
    // Xóa hết các item cũ trong menuList
    menuList.removeAll();
    // Thêm các item còn lại từ vị trí 1 trong danh sách userAccount vào menuList
    for (int i = 0; i < userAccount.size(); i++) {
        Model_User_Account user = userAccount.get(i);
        menuList.add(new Item_People(user), "wrap");
    }
    // Cập nhật lại giao diện menuList
    refreshMenuList();
}


    private void showGroup() {
        //  test data
        menuList.removeAll();

        refreshMenuList();
    }

    private void showBox() {
        //  test data
        menuList.removeAll();

        refreshMenuList();
    }

    private void refreshMenuList() {
        menuList.repaint();
        menuList.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JLayeredPane();
        menuMessage = new com.raven.component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(204, 204, 204));

        menu.setBackground(new java.awt.Color(255, 255, 255));
        menu.setOpaque(true);
        menu.setLayout(new java.awt.GridLayout(1, 3));

        menuMessage.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/message_selected.png"))); // NOI18N
        menuMessage.setIconSimple(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/message.png"))); // NOI18N
        menuMessage.setSelected(true);
        menuMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMessageActionPerformed(evt);
            }
        });
        menu.add(menuMessage);

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        menuList.setBackground(new java.awt.Color(255, 255, 255));
        menuList.setOpaque(true);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 576, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(menu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(sp))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMessageActionPerformed
        if (!menuMessage.isSelected()) {
            menuMessage.setSelected(true);
//            menuGroup.setSelected(false);
//            menuBox.setSelected(false);
            showMessage();
        }
    }//GEN-LAST:event_menuMessageActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane menu;
    private javax.swing.JLayeredPane menuList;
    private com.raven.component.MenuButton menuMessage;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
