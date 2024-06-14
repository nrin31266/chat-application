package com.raven.component;

import com.raven.event.EventFile;
import com.raven.event.EventFileReceiver;
import com.raven.event.EventFileSender;
import com.raven.event.PublicEvent;
import com.raven.model.Model_File_Sender;
import com.raven.model.Model_HistoryChat;
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
        PublicEvent.getInstance().addEventFile(new EventFile() {
            @Override
            public String fileSizeConversion(String fileSize) {
                Double sizeBytes = Double.parseDouble(fileSize);
                if (sizeBytes == 0) {
                    return "0B";
                }
                String[] sizeUnits = {"B", "KB", "MB", "GB", "TB", "PB"};
                int unitIndex = (int) (Math.log(sizeBytes) / Math.log(1024));
                double convertedSize = sizeBytes / Math.pow(1024, unitIndex);
                return String.format("%.2f %s", convertedSize, sizeUnits[unitIndex]);
            }

            @Override
            public Icon getIconFile(String fEx) {
                if (fEx.equals(".docx") || fEx.equals(".doc")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/doc_docx.png"));
                } else if (fEx.equals(".pptx")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/pptx.png"));
                } else if (fEx.equals(".txt")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/txt.png"));
                } else if (fEx.equals(".mp3")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/mp3.png"));
                } else if (fEx.equals(".mp4")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/mp4.png"));
                } else if (fEx.equals(".pdf")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/pdf.png"));
                } else if (fEx.equals(".html")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/html.png"));
                } else if (fEx.equals(".css")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/css.png"));
                } else if (fEx.equals(".zip")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/zip.png"));
                } else if (fEx.equals(".7z")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/7z.png"));
                } else if (fEx.equals(".jpg") || fEx.equals(".png") || fEx.equals(".jpeg") || fEx.equals(".gif")) {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/img.png"));
                } else {
                    return new ImageIcon(getClass().getResource("/com/raven/icon/file.png"));
                }
            }

            @Override
            public String getFileExtension(String fileName) {
                if (fileName == null || fileName.lastIndexOf('.') == -1) {
                    return ""; // Trả về chuỗi rỗng nếu không có phần mở rộng
                }
                int lastDotIndex = fileName.lastIndexOf('.');
                return fileName.substring(lastDotIndex);
            }
        });

    }

    public void setFileGHistory(Model_HistoryChat data, int mode) {
        if (mode == 0) {
            mode = 0;
            progress.setVisible(false);
//                cmdDow.setVisible(true);
            lbName.setText(data.getFileName());
            lbFileSize.setText(PublicEvent.getInstance().getEventFile().fileSizeConversion(data.getFileSize()));
            pic.setImage(PublicEvent.getInstance().getEventFile().getIconFile(PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath())));
            cmdLocation.setVisible(true);
            cmdLocation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PublicEvent.getInstance().getEventDownFile().localFileSender(data.getFileID(), PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath()), data.getSenderFilePath());
                }
            });
        } else if (mode == 1) {
            mode = 1;
            //
            progress.setVisible(false);
            cmdDow.setVisible(true);
            cmdLocation.setVisible(true);
            lbName.setText(data.getFileName());
            lbFileSize.setText(PublicEvent.getInstance().getEventFile().fileSizeConversion(data.getFileSize()));
            pic.setImage(PublicEvent.getInstance().getEventFile().getIconFile(PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath())));
            cmdLocation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PublicEvent.getInstance().getEventDownFile().localFileReceiver(data.getFileID(), PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath()));
                }
            });
            cmdDow.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PublicEvent.getInstance().getEventDownFile().downFile(data.getFileID(), PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath()), data.getFileName());
                }

            });
        }
    }

    public void setFile(Model_File_Sender fileSender) {
        fileSender.addEvent(new EventFileSender() {
            private String fileName;
            @Override
            public void onSending(double percentage) {
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {

            }

            @Override
            public void onFinish() {
                mode = 0;
                progress.setVisible(false);
//                cmdDow.setVisible(true);
                lbName.setText(fileSender.getFileName());
                lbFileSize.setText(PublicEvent.getInstance().getEventFile().fileSizeConversion(fileSender.getFileSize() + ""));
                pic.setImage(PublicEvent.getInstance().getEventFile().getIconFile(fileSender.getFileExtensions()));
                cmdLocation.setVisible(true);
                cmdLocation.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PublicEvent.getInstance().getEventDownFile().localFileSender(fileSender.getFileID(), fileSender.getFileExtensions(), fileSender.getFile().getAbsolutePath());
                    }
                });
            }

        });
    }

    public void setFile(Model_Receive_File data) {

        try {
            Service.getInstance().addFileReceiver(data.getFileID(), new EventFileReceiver() {
                private String fileName;
                @Override
                public void onReceiving(double percentage) {
                    progress.setValue(((int) percentage));
                }

                @Override
                public void onStartReceiving() {

                }

                @Override
                public void onFinish(File file, int fileID, String fileExtension, String fileName, String fileSize) {
                    mode = 1;
                    //
                    progress.setVisible(false);
                    cmdDow.setVisible(true);
                    cmdLocation.setVisible(true);
                    lbName.setText(data.getFileName());

                    lbFileSize.setText(PublicEvent.getInstance().getEventFile().fileSizeConversion(fileSize));
                    pic.setImage(PublicEvent.getInstance().getEventFile().getIconFile(fileExtension));
                    cmdLocation.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PublicEvent.getInstance().getEventDownFile().localFileReceiver(fileID, fileExtension);
                        }
                    });
                    cmdDow.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            PublicEvent.getInstance().getEventDownFile().downFile(fileID, fileExtension, fileName);
                        }

                    });
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
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

        cmdLocation.setBackground(new java.awt.Color(218, 218, 218));
        cmdLocation.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/folder.png"))); // NOI18N
        cmdLocation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdLocationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdLocationMouseExited(evt);
            }
        });
        cmdLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLocationActionPerformed(evt);
            }
        });

        cmdDow.setBackground(new java.awt.Color(218, 218, 218));
        cmdDow.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/down_black.png"))); // NOI18N
        cmdDow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdDowMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdDowMouseExited(evt);
            }
        });

        jLayeredPane1.setLayer(cmdLocation, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(cmdDow, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmdLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdDow, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(cmdLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdDow, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLocationActionPerformed

    }//GEN-LAST:event_cmdLocationActionPerformed

    private void cmdLocationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdLocationMouseEntered
        // TODO add your handling code here:
        cmdLocation.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdLocationMouseEntered

    private void cmdLocationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdLocationMouseExited
        cmdLocation.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdLocationMouseExited

    private void cmdDowMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdDowMouseEntered
        cmdDow.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdDowMouseEntered

    private void cmdDowMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdDowMouseExited
        cmdDow.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdDowMouseExited


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
