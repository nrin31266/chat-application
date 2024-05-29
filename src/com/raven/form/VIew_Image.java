package com.raven.form;

import com.raven.event.PublicEvent;
import com.raven.model.Model_Receive_Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.SwingUtilities;

public class VIew_Image extends javax.swing.JComponent {

    public VIew_Image() {
        initComponents();
    }

    private Icon image;
    private String mode;
    int fileID;
    String fileExcetion;
    String fileName;

    public void viewImage(Icon image, String mode, int fileID, String fileExcetion, String fileName) {
        this.mode = mode;
        this.fileID=fileID;
        this.fileExcetion=fileExcetion;
        this.fileName=fileName;

        if (mode.equals("sender")) {
            cmdSave.setVisible(false);
            cmdLocation.setVisible(true);
        } else {
            cmdSave.setVisible(true);
            cmdLocation.setVisible(true);
        }
        this.image = image;
        pic.setImage(image);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cmdSave = new com.raven.component.OptionButton();
        cmdLocation = new com.raven.component.OptionButton();
        pic = new com.raven.swing.PictureBox();

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        cmdSave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cmdSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cmdSave.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/down.png"))); // NOI18N
        cmdSave.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdSave.setMinimumSize(new java.awt.Dimension(20, 20));
        cmdSave.setPreferredSize(new java.awt.Dimension(20, 20));
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        jPanel1.add(cmdSave);

        cmdLocation.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cmdLocation.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cmdLocation.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/folder.png"))); // NOI18N
        cmdLocation.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdLocation.setMinimumSize(new java.awt.Dimension(20, 20));
        cmdLocation.setPreferredSize(new java.awt.Dimension(20, 20));
        cmdLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLocationActionPerformed(evt);
            }
        });
        jPanel1.add(cmdLocation);

        pic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                picMousePressed(evt);
            }
        });

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 885, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void picMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_picMousePressed
        if (SwingUtilities.isLeftMouseButton(evt)) {
            setVisible(false);
        }
    }//GEN-LAST:event_picMousePressed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        PublicEvent.getInstance().getEventImageView().saveImage(fileID, fileExcetion, fileName);
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void cmdLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLocationActionPerformed
        PublicEvent.getInstance().getEventImageView().viewLocation(mode, fileID, fileExcetion, fileName);
    }//GEN-LAST:event_cmdLocationActionPerformed

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdLocation;
    private com.raven.component.OptionButton cmdSave;
    private javax.swing.JPanel jPanel1;
    private com.raven.swing.PictureBox pic;
    // End of variables declaration//GEN-END:variables
}
