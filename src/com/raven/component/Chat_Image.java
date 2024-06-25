package com.raven.component;

import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_HistoryChat;
import com.raven.model.Model_Receive_Image;
import com.raven.swing.PictureBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class Chat_Image extends javax.swing.JLayeredPane {

//    private final String icon = "/com/raven/icon/errorimage.png";
    public Chat_Image(boolean right) {
        initComponents();
        setLayout(new MigLayout("", "0[" + (right ? "right" : "left") + "]0", "3[]3"));
    }

    public void addImage(Model_File_Sender fileSender) {
        Icon image = new ImageIcon(fileSender.getFile().getAbsolutePath());
        Image_Item pic = new Image_Item();

        pic.setPreferredSize(getAutoSize(image, 200, 200));
        pic.setImage(image, fileSender);
        Model_Receive_Image dataReceiver
                = new Model_Receive_Image(fileSender.getFileID(), null, 0, 0, fileSender.getFileExtensions(), fileSender.getFileName());
        dataReceiver.setFile(fileSender.getFile());
        addEvent(pic, image, "sender", fileSender.getFileID(), fileSender.getFileExtensions(), fileSender.getFile().getAbsolutePath());
        add(pic, "wrap");
    }

    public void addImage(Model_Receive_Image dataImage) {
        Image_Item pic = new Image_Item();
        pic.setPreferredSize(new Dimension(dataImage.getWidth(), dataImage.getHeight()));  //  for test update next
        pic.setImage(dataImage);
        pic.setChatImage(this);
        add(pic, "wrap");
    }

    public void addImageChatHistorySender(Model_HistoryChat data) {
        Icon image = new ImageIcon(data.getSenderFilePath());
        Image_Item pic = new Image_Item();
        pic.setPreferredSize(getAutoSize(image, 200, 200));
        if (image.getIconWidth() == -1) {
//            pic.setImage(new ImageIcon(getClass().getResource(icon)));
            Label lb= new Label("Không tìm thấy ảnh do lỗi!");
            lb.setForeground(Color.red);
            add(lb);
        } else {
            pic.setImage(image);
//            String fex = PublicEvent.getInstance().getEventFile().getFileExtension(data.getFileName());
            String fileEx = getFileExtension(data.getFileName());
            addEvent(pic, image, "sender", data.getFileID(), fileEx, data.getSenderFilePath());
            pic.getProgress().setVisible(false);
            add(pic, "wrap");
        }
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return ""; // Trả về chuỗi rỗng nếu không có phần mở rộng
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return fileName.substring(lastDotIndex);
    }

    void addEvent(Component com, Icon image, String mode, int fileID, String fileExcetion, String fileName) {
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    PublicEvent.getInstance().getEventImageView().viewImage(image, mode, fileID, fileExcetion, fileName);
                }
            }
        });
    }

    public void addImageChatHistoryReceiver(Model_HistoryChat data) {
        File file = new File(data.getReceiverFilePath());
        String absolutePath = file.getAbsolutePath();

        
        Icon image = new ImageIcon(absolutePath);
        Image_Item pic = new Image_Item();
        pic.setPreferredSize(getAutoSize(image, 200, 200));
        if (image.getIconWidth() == -1) {
//            pic.setImage(new ImageIcon(getClass().getResource(icon)));
            Label lb= new Label("Không tìm thấy ảnh do lỗi!");
            lb.setForeground(Color.red);
            add(lb);
        } else {
            pic.setImage(image);
            String fileEx = getFileExtension(data.getFileName());
            addEvent(pic, image, "receiver", data.getFileID(), fileEx, data.getFileName());
            pic.getProgress().setVisible(false);
            add(pic, "wrap");
        }
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
