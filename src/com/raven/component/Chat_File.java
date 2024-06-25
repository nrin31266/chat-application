package com.raven.component;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javazoom.jl.decoder.JavaLayerException;

public class Chat_File extends javax.swing.JPanel {

    private int mode;
    private MP3Player mp3Player;
    private Timer timer;

    public Chat_File() {
        initComponents();
        mp3Player = new MP3Player();
        cmdPlayAndPause.setVisible(false);
        timeLabel.setVisible(false);
        //
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
            if (!fileExists(data.getSenderFilePath(), 1)) {
                setIfoFileError();
                return;
            }
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
            if (PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath()).equals(".mp3")) {
                timeLabel.setVisible(true);
                cmdPlayAndPause.setVisible(true);
                loadFileMp3(data.getSenderFilePath(), 1);
            }
        } else if (mode == 1) {
            if (!fileExists(data.getReceiverFilePath(), 2)) {
                setIfoFileError();
                return;
            }
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
            if (PublicEvent.getInstance().getEventFile().getFileExtension(data.getSenderFilePath()).equals(".mp3")) {
                cmdPlayAndPause.setVisible(true);
                timeLabel.setVisible(true);
                File file = new File(data.getReceiverFilePath());
                String absolutePath = file.getAbsolutePath();
                loadFileMp3(absolutePath, 2);
            }
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
                if (fileSender.getFileExtensions().equals(".mp3")) {
                    timeLabel.setVisible(true);
                    cmdPlayAndPause.setVisible(true);
                    loadFileMp3(fileSender.getFile().getPath(), 1);
                }
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
                            PublicEvent.getInstance().getEventDownFile().downFile(fileID, PublicEvent.getInstance().getEventFile().getFileExtension(file.getName()), file.getName());
                        }
                    });
                    if (data.getFileExtension().equals(".mp3")) {
                        timeLabel.setVisible(true);
                        cmdPlayAndPause.setVisible(true);
                        String filePath = "client_data/" + data.getFileID() + data.getFileExtension();
                        File f = new File(filePath);
                        String absolutePath = f.getAbsolutePath();
                        System.out.println("file abs rc: " + absolutePath);
                        loadFileMp3(absolutePath, 2);
                    }
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
        cmdPlayAndPause = new com.raven.component.OptionButton();
        timeLabel = new javax.swing.JLabel();

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
        cmdDow.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/dow_blue.png"))); // NOI18N
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
                .addComponent(cmdDow, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cmdPlayAndPause.setBackground(new java.awt.Color(218, 218, 218));
        cmdPlayAndPause.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/play.png"))); // NOI18N
        cmdPlayAndPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdPlayAndPauseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdPlayAndPauseMouseExited(evt);
            }
        });
        cmdPlayAndPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPlayAndPauseActionPerformed(evt);
            }
        });

        timeLabel.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdPlayAndPause, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmdPlayAndPause, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void cmdPlayAndPauseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdPlayAndPauseMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdPlayAndPauseMouseEntered

    private void cmdPlayAndPauseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdPlayAndPauseMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdPlayAndPauseMouseExited

    private void cmdPlayAndPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPlayAndPauseActionPerformed
        //Phát nhạc
        if (mp3Player.isPlaying()) {
            pauseMP3();
        } else {
            playMP3();
        }
    }//GEN-LAST:event_cmdPlayAndPauseActionPerformed
    public void playMP3() {
        new Thread(() -> {
            try {
                mp3Player.play();
                cmdPlayAndPause.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/pause.png")));
                startTimer();
            } catch (FileNotFoundException | JavaLayerException e) {

            }
        }).start();
    }

    public void pauseMP3() {
        mp3Player.pause();
        cmdPlayAndPause.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/play.png")));
        stopTimer();
    }

    private void loadFileMp3(String filePath, int model) {
        try {
            mp3Player.loadFile(filePath);
            timeLabel.setText("00:00 / " + formatTime(mp3Player.getTotalDuration()));
            cmdPlayAndPause.setEnabled(true);
        } catch (IOException ex) {
            Logger.getLogger(Chat_File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(Chat_File.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedTagException ex) {
            Logger.getLogger(Chat_File.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void startTimer() {
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mp3Player.isPlaying()) {
                    cmdPlayAndPause.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/play.png")));
                    stopTimer();
                }
                updateTimer();
//                progressPanel.repaint();
            }
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void setIfoFileError() {
        pic.setImage(new ImageIcon(getClass().getResource("/com/raven/icon/file_error.png")));
        lbName.setText("File lỗi hoặc đã bị xóa!");
        lbName.setForeground(Color.red);
        lbFileSize.setText("");
        progress.setVisible(false);
    }

    private void updateTimer() {
        int currentSeconds = mp3Player.getCurrentPosition();
        timeLabel.setText(formatTime(currentSeconds) + " / " + formatTime(mp3Player.getTotalDuration()));
    }

    private String formatTime(int seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long remainingSeconds = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private boolean fileExists(String filePath, int mode) {
        if (mode == 2) {
            File file = new File(filePath);
            filePath = file.getAbsolutePath();
        }

        return new File(filePath).exists();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.component.OptionButton cmdDow;
    private com.raven.component.OptionButton cmdLocation;
    private com.raven.component.OptionButton cmdPlayAndPause;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbFileSize;
    private javax.swing.JLabel lbName;
    private com.raven.swing.PictureBox pic;
    private com.raven.swing.Progress progress;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
