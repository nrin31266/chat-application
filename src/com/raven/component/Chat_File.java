package com.raven.component;

import com.raven.event.EventFileReceiver;
import com.raven.event.EventFileSender;
import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_Receive_File;
import com.raven.model.Model_Receive_Image;
import com.raven.service.Service;
import com.raven.swing.blurHash.BlurHash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Chat_File extends javax.swing.JPanel {
    private int mode;
    
    public Chat_File() {
        initComponents();
        setOpaque(false);
        cmdDow.setVisible(false);
        cmdLocation.setVisible(false);
        progress.setBorder(null);
    }

    public void setFile(Model_File_Sender fileSender) {
        fileSender.addEvent(new EventFileSender() {
            @Override
            public void onSending(double percentage) {
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {

            }

            @Override
            public void onFinish() {
                mode=0;
                progress.setVisible(false);
//                cmdDow.setVisible(true);
                lbFileSize.setText(fileSizeConversion(fileSender.getFileSize() + ""));
                lbName.setText(fileSender.getFileName());
                setIconPic(fileSender.getFileExtensions());
                cmdLocation.setVisible(true);
                cmdLocation.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PublicEvent.getInstance().getEventDownFile().localFileSender(fileSender);
                    }
                    
                });
            }

        });
    }

    public void setFile(Model_Receive_File data) {

        try {
            Service.getInstance().addFileReceiver(data.getFileID(), new EventFileReceiver() {
                @Override
                public void onReceiving(double percentage) {
                    progress.setValue(((int) percentage));
                }

                @Override
                public void onStartReceiving() {

                }

                @Override
                public void onFinish(File file) {
                    mode=1;
                    //
                    String fileName = data.getFileName();
                    String fileSize = data.getFileSize();
                    String fileExtension = data.getFileExtension();
                    //
                    progress.setVisible(false);
                    cmdDow.setVisible(true);
                    cmdLocation.setVisible(true);
                    lbName.setText(fileName);
                    lbFileSize.setText(fileSizeConversion(fileSize));
                    setIconPic(fileExtension);
                    cmdLocation.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PublicEvent.getInstance().getEventDownFile().localFileReceiver(data);
                        }     
                    });
                    cmdDow.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PublicEvent.getInstance().getEventDownFile().downFile(data);
                        }
                        
                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileSizeConversion(String fileSize) {

        Double sizeBytes = Double.parseDouble(fileSize);

        if (sizeBytes == 0) {
            return "0B";
        }

        String[] sizeUnits = {"B", "KB", "MB", "GB", "TB", "PB"};
        int unitIndex = (int) (Math.log(sizeBytes) / Math.log(1024));
        double convertedSize = sizeBytes / Math.pow(1024, unitIndex);
        return String.format("%.2f %s", convertedSize, sizeUnits[unitIndex]);
    }
    
    public void setIconPic(String fEx) {
        if (fEx.equals(".docx") || fEx.equals(".doc")) {
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/doc_docx.png")));

        } else if(fEx.equals(".pptx")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/pptx.png")));
        }else if(fEx.equals(".txt")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/txt.png")));
        }
        else if(fEx.equals(".mp3")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/mp3.png")));
        }else if(fEx.equals(".mp4")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/mp4.png")));
        }else if(fEx.equals(".pdf")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/pdf.png")));
        }
        else if(fEx.equals(".html")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/html.png")));
        }else if(fEx.equals(".css")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/css.png")));
        }else if(fEx.equals(".zip")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/zip.png")));
        }else if(fEx.equals(".7z")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/7z.png")));
        }else if(fEx.equals(".jpg")||fEx.equals(".png")||fEx.equals(".jpeg")||fEx.equals(".gif")){
            pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/img.png")));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.raven.swing.PictureBox();
        progress = new com.raven.swing.Progress();
        jPanel1 = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        lbFileSize = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        cmdLocation = new com.raven.component.OptionButton();
        cmdDow = new com.raven.component.OptionButton();

        pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/file.png"))); // NOI18N
        pic.setMinimumSize(new java.awt.Dimension(48, 48));

        progress.setForeground(new java.awt.Color(0, 51, 204));
        progress.setToolTipText("");
        progress.setValue(20);
        progress.setProgressType(com.raven.swing.Progress.ProgressType.FILE);

        pic.setLayer(progress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        lbName.setText("fileName");
        jPanel1.add(lbName);

        lbFileSize.setForeground(new java.awt.Color(7, 98, 153));
        lbFileSize.setText("size=?");
        jPanel1.add(lbFileSize);

        jLayeredPane1.setLayout(new javax.swing.BoxLayout(jLayeredPane1, javax.swing.BoxLayout.Y_AXIS));

        cmdLocation.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/folder.png"))); // NOI18N
        cmdLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLocationActionPerformed(evt);
            }
        });
        jLayeredPane1.add(cmdLocation);

        cmdDow.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/down_black.png"))); // NOI18N
        jLayeredPane1.add(cmdDow);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLocationActionPerformed

    }//GEN-LAST:event_cmdLocationActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdDow;
    private com.raven.component.OptionButton cmdLocation;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFileSize;
    private javax.swing.JLabel lbName;
    private com.raven.swing.PictureBox pic;
    private com.raven.swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
