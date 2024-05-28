package com.raven.form;

import com.raven.event.PublicEvent;
import com.raven.model.Model_Profile;
import com.raven.model.Model_User_Account;
import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {

    private Chat chat ;
    private Menu menu;
    private Model_Profile modelProfile;

    public Model_Profile getModelProfile() {
        return modelProfile;
    }

    public void setModelProfile(Model_Profile modelProfile) {
        this.modelProfile = modelProfile;
        menu.setModelProfile(modelProfile);
    }

    public Menu getMenu() {
        return menu;
    }

    
    public Home() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[50!]0[200!]0[fill, 100%]0", "0[fill]0"));
        menu=new Menu();
        this.add(menu);
        this.add(new Menu_Left());  
        chat = new Chat();
        this.add(chat);
        chat.setVisible(false);
        /////profile
        
        
    }
    public void setUser(Model_User_Account user){
        System.out.println("Home select id: "+user.getUserID());
        chat.setUser(user);
        chat.setVisible(true);
        
    }
    public void  updateUser(Model_User_Account user){
        chat.updateUser(user);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 649, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
