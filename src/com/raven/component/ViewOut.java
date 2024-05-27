/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.component;

import javax.swing.JLabel;


public class ViewOut extends javax.swing.JPanel {

    public JLabel getLbContent() {
        return lbContent;
    }

    public void setLbContent(JLabel lbContent) {
        this.lbContent = lbContent;
    }

    public JLabel getLbTitle() {
        return lbTitle;
    }

    public void setLbTitle(JLabel lbTitle) {
        this.lbTitle = lbTitle;
    }

    public ViewOut() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        lbContent = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(500, 25));
        setMinimumSize(new java.awt.Dimension(0, 25));
        setPreferredSize(new java.awt.Dimension(500, 25));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbTitle.setText("lbTitle");
        lbTitle.setMaximumSize(new java.awt.Dimension(70, 25));
        lbTitle.setMinimumSize(new java.awt.Dimension(70, 25));
        lbTitle.setPreferredSize(new java.awt.Dimension(70, 25));
        add(lbTitle);

        lbContent.setText("lbContent");
        lbContent.setMaximumSize(new java.awt.Dimension(300, 25));
        lbContent.setMinimumSize(new java.awt.Dimension(300, 25));
        lbContent.setPreferredSize(new java.awt.Dimension(300, 25));
        add(lbContent);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbContent;
    private javax.swing.JLabel lbTitle;
    // End of variables declaration//GEN-END:variables
}
