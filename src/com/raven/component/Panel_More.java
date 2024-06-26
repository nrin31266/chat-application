package com.raven.component;

import com.raven.app.MessageType;
import com.raven.emoji.Emoji;
import com.raven.emoji.Model_Emoji;
import com.raven.event.PublicEvent;
import com.raven.main.Main;
import com.raven.model.Model_Send_Message;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.WrapLayout;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import com.raven.swing.ScrollBar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import net.miginfocom.swing.MigLayout;

public class Panel_More extends javax.swing.JPanel {

    public Panel_More() {
        initComponents();
        init();
        this.setBackground(new Color(220, 205, 223));
    }

    private void init() {
        setLayout(new MigLayout("fillx"));
        panelHeader = new JLayeredPane();

        panelHeader.add(getButtonImage());
//        panelHeader.setBackground(new Color(220, 205, 223));
        panelHeader.add(getButtonFile());
        panelHeader.add(getEmojiStyle1());
        
        //
        panelHeader.add(getEmojiStyle2());
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(panelHeader, "w 100%, h 30!, wrap");

        panelDetail = new JPanel();
        panelDetail.setBackground(new Color(235, 235, 235));
        JScrollPane ch = new JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new ScrollBar());

        add(ch, "w 100%, h 100%");

        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));
        for (Model_Emoji d : Emoji.getInstance().getStyle1()) {
            panelDetail.add(getButton(d));
        }
//        panelDetail.repaint();
//        panelDetail.revalidate();

    }

    private JButton getButtonImage() {
        OptionButton cmd = new OptionButton();
        cmd.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/image.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser ch = new JFileChooser();
                ch.setMultiSelectionEnabled(true);
                ch.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory() || isImageFile(file);
                    }

                    @Override
                    public String getDescription() {
                        return "Image File";
                    }

                });
                int option = ch.showOpenDialog(Main.getFrames()[0]);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File files[] = ch.getSelectedFiles();
                    try {
                        for (File file : files) {
                            Model_Send_Message message = new Model_Send_Message(MessageType.IMAGE, Service.getInstance().getUser().getUserID(), user.getUserID(), "");
                            Service.getInstance().addFile(file, message);
                            PublicEvent.getInstance().getEventChat().sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return cmd;
    }

    private JButton getButtonFile() {
        OptionButton cmd = new OptionButton();
        cmd.setIconSelected(new ImageIcon(getClass().getResource("/com/raven/icon/link.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ape) {
                JFileChooser ch = new JFileChooser();
                ch.setMultiSelectionEnabled(true);
                int option = ch.showOpenDialog(Main.getFrames()[0]);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File files[] = ch.getSelectedFiles();
                    for (File file : files) {
                        try {
                            Model_Send_Message message = new Model_Send_Message(MessageType.FILE, Service.getInstance().getUser().getUserID(), user.getUserID(), "");
                            Service.getInstance().addFile(file, message);
                            PublicEvent.getInstance().getEventChat().sendMessage(message);
                        } catch (IOException ex) {
                            Logger.getLogger(Panel_More.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }

        });

        return cmd;
    }

    private JButton getEmojiStyle1() {
        OptionButton cmd = new OptionButton();
        cmd.setIconSelected(Emoji.getInstance().getImoji(1).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emoji.getInstance().getStyle1()) {
                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }

        });
        cmd.setSelected(true);
        return cmd;
    }

    private JButton getEmojiStyle2() {
        OptionButton cmd = new OptionButton();
        cmd.setIconSelected(Emoji.getInstance().getImoji(34).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();
                for (Model_Emoji d : Emoji.getInstance().getStyle2()) {

                    panelDetail.add(getButton(d));
                }
                panelDetail.repaint();
                panelDetail.revalidate();
            }

        });

        return cmd;
    }

    private JButton getButton(Model_Emoji dataEmoji) {
        OptionButton cmd = new OptionButton();
        cmd.setName(dataEmoji.getId() + "");
        cmd.setIconSelected(dataEmoji.getIcon());
        cmd.setPreferredSize(new Dimension(50, 50));
        cmd.setBorder(new EmptyBorder(3, 3, 3, 3));
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setContentAreaFilled(false);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model_Send_Message message = new Model_Send_Message(MessageType.EMOJI, Service.getInstance().getUser().getUserID(), user.getUserID(), dataEmoji.getId() + "");
                sendMessage(message);
                PublicEvent.getInstance().getEventChat().sendMessage(message);
            }
        });
        return cmd;
    }

    private void sendMessage(Model_Send_Message data) {
        Service.getInstance().getClient().emit("send_to_user", data.toJsonObject());
    }

    private void clearSelected() {
        for (Component c : panelHeader.getComponents()) {
            if (c instanceof OptionButton) {
                ((OptionButton) c).setSelected(false);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
    }
    private JLayeredPane panelHeader;
    private JPanel panelDetail;
    private Model_User_Account user;

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
