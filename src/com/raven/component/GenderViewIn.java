
package com.raven.component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class GenderViewIn extends javax.swing.JPanel {
    public GenderViewIn() {
        initComponents();
    }
    private void init(){
    }

    public JComboBox<String> getComboBoxGender() {
        return comboBoxGender;
    }

    public void setComboBoxGender(JComboBox<String> comboBoxGender) {
        this.comboBoxGender = comboBoxGender;
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        comboBoxGender = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(0, 25));
        setPreferredSize(new java.awt.Dimension(400, 25));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Giới tính");
        add(jLabel1);

        comboBoxGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác", "Không" }));
        add(comboBoxGender);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxGender;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
