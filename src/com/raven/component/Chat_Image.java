package com.raven.component;

import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_Image;
import com.raven.swing.PictureBox;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class Chat_Image extends javax.swing.JLayeredPane {

    public Chat_Image(boolean right) {
        initComponents();
        setLayout(new MigLayout("", "0[" + (right ? "right" : "left") + "]0", "3[]3"));
    }

    public void addImage(Model_File_Sender fileSender) {
        Icon image = new ImageIcon(fileSender.getFile().getAbsolutePath());
        Image_Item pic = new Image_Item();

        pic.setPreferredSize(getAutoSize(image, 200, 200));
        pic.setImage(image, fileSender);
        Model_Receive_Image dataReceiver=
                new Model_Receive_Image(fileSender.getFileID(), null, 0, 0, fileSender.getFileExtensions(), fileSender.getFileName());
        dataReceiver.setFile(fileSender.getFile());
        addEvent(pic, image, "sender", dataReceiver);
        add(pic, "wrap");
    }

    public void addImage(Model_Receive_Image dataImage) {
        

        Image_Item pic = new Image_Item();
        pic.setPreferredSize(new Dimension(dataImage.getWidth(), dataImage.getHeight()));  //  for test update next
        pic.setImage(dataImage);
        pic.setChatImage(this);
        add(pic, "wrap");
        
    }

    void addEvent(Component com, Icon image, String mode, Model_Receive_Image data) {
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    PublicEvent.getInstance().getEventImageView().viewImage(image, mode, data);
                }
            }
        });
    }

    private Dimension getAutoSize(Icon image, int w, int h) {
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
