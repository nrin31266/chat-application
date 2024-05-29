package com.raven.form;

import com.raven.app.MessageType;
import com.raven.event.EventViewProfile;
import com.raven.event.PublicEvent;
import com.raven.main.Main;
import com.raven.model.Model_Image_Update;
import com.raven.model.Model_Name_Update;
import com.raven.model.Model_Profile;
import com.raven.model.Model_Profile_Update;
import com.raven.model.Model_Receive_Image;
import com.raven.model.Model_Send_Message;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.ScrollBar;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class View_Profile extends javax.swing.JPanel {
    
    private final String pathAvatarBasic = "/com/raven/icon/profile.png";
    private final String pathCoverArtBasic = "/com/raven/icon/testing/dog.jpg";
    private Model_Profile modelProfile;
    
    public Model_Profile getModelProfile() {
        return modelProfile;
    }
    
    public void setModelProfile(Model_Profile modelProfile) {
        this.modelProfile = modelProfile;
    }
    
    public View_Profile() {
        initComponents();
        init();
        
        PublicEvent.getInstance().addEventViewProfile(new EventViewProfile() {
            @Override
            public void setProfile(Model_Profile dataPr, int mode) {
                modelProfile = dataPr;
                //System.out.println(dataPr.toString());        
                refreshView();
                setDataView();
                //setView
                info2.setVisible(false);
                info1.setVisible(true);
                panelName2.setVisible(false);
                panelName1.setVisible(true);
                cmdEditProfileClose.setVisible(false);
                cmdEditProfileOk.setVisible(false);
                //

                //
                if (dataPr.getName() != null && !dataPr.getName().equals("")) {
                    lbSetName.setText(dataPr.getName());
                    
                }
                if (mode == 1) {

                    //
                    setDataViewEdit();
                    cmdSetCoverArt.setVisible(true);
                    cmdEditName.setVisible(true);
                    cmdEditProfile.setVisible(true);
                    //setEdit
                    //
                    if (dataPr.getImage() != null && !dataPr.getImage().isEmpty()) {
                        avt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getImage()));
                        avt.repaint();
                    }
                    if (dataPr.getCoverArt() != null && !dataPr.getCoverArt().isEmpty()) {
                        coverArt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getCoverArt()));
                        coverArt.repaint();
                    }
                } else if (mode == 2) {
                    //Loại bỏ những thứ khi xem
                    cmdSetCoverArt.setVisible(false);
                    cmdEditName.setVisible(false);
                    cmdEditProfile.setVisible(false);
                    cmdEditProfileClose.setVisible(false);
                    cmdEditProfileOk.setVisible(false);
                    //

                    if (dataPr.getImage() != null && !dataPr.getImage().isEmpty()) {
                        avt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getImage()));
                        avt.repaint();
                    }
                    if (dataPr.getCoverArt() != null && !dataPr.getCoverArt().isEmpty()) {
                        coverArt.setImage(PublicEvent.getInstance().getEventProfile().createImage(dataPr.getCoverArt()));
                        coverArt.repaint();
                    }
                }
            }
            
        });
    }
    
    public void refreshView() {
        avt.setImage(new ImageIcon(getClass().getResource(pathAvatarBasic)));
        coverArt.setImage(new ImageIcon(getClass().getResource(pathCoverArtBasic)));
        lbSetName.setText("Chưa đặt tên");
        viewOutAddress.getLbContent().setText("Chưa cập nhập");
        viewOutDate.getLbContent().setText("Chưa cập nhập");
        viewOutEmail.getLbContent().setText("Chưa cập nhập");
        viewOutGender.getLbContent().setText("Chưa cập nhập");
        viewOutPhone.getLbContent().setText("Chưa cập nhập");
        
    }
    
    public void init() {
        scroll.setVerticalScrollBar(new ScrollBar());
        setOpaque(false);
        stackAvatarOnCoverArt();
        info2.setVisible(false);
        info1.setVisible(true);
        panelName2.setVisible(false);
        panelName1.setVisible(true);
        cmdEditProfileClose.setVisible(false);
        cmdEditProfileOk.setVisible(false);
        //
        viewPhone.getLbName().setText("Sđt");
        viewEmail.getLbName().setText("Email");
        viewAddress.getLbName().setText("Địa chỉ");
        viewUserName.getLbName().setText("Tài khoản");
        viewUserName.setVisible(false);
        //
        viewOutAddress.getLbTitle().setText("Địa chỉ");
        viewOutDate.getLbTitle().setText("Ngày sinh");
        viewOutPhone.getLbTitle().setText("Số đt");
        viewOutEmail.getLbTitle().setText("Email");
        viewOutGender.getLbTitle().setText("Giới tính");
        //
        lbError.setVisible(false);
    }
    
    public void stackAvatarOnCoverArt() {
        int coverArtWidth = 350;
        int coverArtHeight = 150;
        int avatarWidth = 100;
        int avatarHeight = 100;
        int avatarX = 10;
        int avatarY = coverArtHeight - avatarHeight / 2; // Đặt nửa dưới của avt trên coverArt

        // Đặt avt ở giữa phía dưới của pictureCoverArt1
        MenuList.add(avt, javax.swing.JLayeredPane.PALETTE_LAYER);
        avt.setBounds(avatarX, avatarY, avatarWidth, avatarHeight);
    }
    
    public void viewProfile() {
        setVisible(true);
    }
    
    private void setDataViewEdit() {
        if (modelProfile == null) {
            return;
        }
        viewUserName.getTxtContent().setText(modelProfile.getUserName());
        //
        if (modelProfile.getGender() != null && !modelProfile.getGender().isEmpty()) {
            String gender = modelProfile.getGender();
            if (gender.equals("Nam")) {
                genderViewIn.getComboBoxGender().setSelectedItem("Nam");
            } else if (gender.equals("Nữ")) {
                genderViewIn.getComboBoxGender().setSelectedItem("Nữ");
            } else {
                genderViewIn.getComboBoxGender().setSelectedItem("Khác");
            }
        } else {
            genderViewIn.getComboBoxGender().setSelectedItem("Không");
        }
        //
        if (modelProfile.getDate() != null && !modelProfile.getDate().isEmpty()) {
            String dateStr = modelProfile.getDate();
            // Sử dụng split để tách chuỗi thành các phần tử năm, tháng, ngày
            String[] dateParts = dateStr.split("-");

            // Gán các phần tử trong mảng vào các biến tương ứng
            String year = dateParts[0];
            String month = dateParts[1];
            String day = dateParts[2];

            // Đặt giá trị mặc định cho comboBoxMon
            dateViewIn.getComboBoxMon().setSelectedItem(month);
            // Đặt giá trị mặc định cho comboBoxYear
            dateViewIn.getComboBoxYear().setSelectedItem(year);
            // Đặt giá trị mặc định cho comboBoxDay
            dateViewIn.getComBoxDay().setSelectedItem(day);
        }
        viewPhone.getTxtContent().setText(modelProfile.getPhoneNumber());
        viewEmail.getTxtContent().setText(modelProfile.getEmail());
        viewAddress.getTxtContent().setText(modelProfile.getAddress());
        
    }
    
    private void setDataView() {
        if (modelProfile == null) {
            return;
        }
        //
        if (modelProfile.getGender() != null && !modelProfile.getGender().isEmpty()) {
            if (modelProfile.getGender().equals("Không")) {
                viewOutGender.getLbContent().setText("Chưa cập nhập");
            } else {
                viewOutGender.getLbContent().setText(modelProfile.getGender());
            }
        } else {
            viewOutGender.getLbContent().setText("Chưa cập nhập");
        }
        //
        if (modelProfile.getDate() != null && !modelProfile.getDate().isEmpty()) {
            String dateStr = modelProfile.getDate();
            // Sử dụng split để tách chuỗi thành các phần tử năm, tháng, ngày
            String[] dateParts = dateStr.split("-");

            // Gán các phần tử trong mảng vào các biến tương ứng
            String year = dateParts[0];
            String month = dateParts[1];
            String day = dateParts[2];
            
            viewOutDate.getLbContent().setText(day + "/" + month + "/" + year);
        } else {
            viewOutDate.getLbContent().setText("Chưa cập nhập");
        }
        if (modelProfile.getPhoneNumber() != null && !modelProfile.getPhoneNumber().isEmpty()) {
            viewOutPhone.getLbContent().setText(modelProfile.getPhoneNumber());
        } else {
            viewOutPhone.getLbContent().setText("Chưa cập nhập");
        }
        if (modelProfile.getEmail() != null && !modelProfile.getEmail().isEmpty()) {
            viewOutEmail.getLbContent().setText(modelProfile.getEmail());
        } else {
            viewOutEmail.getLbContent().setText("Chưa cập nhập");
        }
        if (modelProfile.getAddress() != null && !modelProfile.getAddress().isEmpty()) {
            viewOutAddress.getLbContent().setText(modelProfile.getAddress());
        } else {
            viewOutAddress.getLbContent().setText("Chưa cập nhập");
        }
    }
    
    private boolean checkDataUpdateProfile() {
        String userName = viewUserName.getTxtContent().getText();
        String gender = "";
        String date = "";
        String phone = viewPhone.getTxtContent().getText();
        String email = viewEmail.getTxtContent().getText();
        String address = viewAddress.getTxtContent().getText();
        if (!modelProfile.getDate().equals("")) {
            date = dateViewIn.getComboBoxYear().getSelectedItem().toString() + "-" + dateViewIn.getComboBoxMon().getSelectedItem().toString() + "-" + dateViewIn.getComBoxDay().getSelectedItem().toString();
        }
        if (!modelProfile.getGender().equals("")) {
            gender = genderViewIn.getComboBoxGender().getSelectedItem().toString();
        }
        if (userName.equals(modelProfile.getUserName())
                && gender.equals(modelProfile.getGender())
                && date.equals(modelProfile.getDate())
                && phone.equals(modelProfile.getPhoneNumber())
                && email.equals(modelProfile.getEmail())
                && address.equals(modelProfile.getAddress())) {
            lbError.setVisible(true);
            lbError.setText("Information hasn't changed!");
            return false;
        }
        lbError.setVisible(false);
        return true;
    }
    
    private boolean isBlankOrWhitespace(String input) {
        // Loại bỏ dấu cách ở đầu và cuối chuỗi
        String trimmedInput = input.trim();

        // Kiểm tra xem chuỗi sau khi loại bỏ dấu cách có rỗng không
        return trimmedInput.isEmpty();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();
        title = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmdClose = new com.raven.component.OptionButton();
        scroll = new javax.swing.JScrollPane();
        MenuList = new javax.swing.JLayeredPane();
        layoutName = new javax.swing.JLayeredPane();
        panelName1 = new javax.swing.JPanel();
        lbSetName = new javax.swing.JLabel();
        cmdEditName = new com.raven.component.OptionButton();
        panelName2 = new javax.swing.JPanel();
        txtSetName = new javax.swing.JTextField();
        cmdEditNameClose = new com.raven.component.OptionButton();
        cmdEditNameOk = new com.raven.component.OptionButton();
        coverArt = new com.raven.swing.PictureCoverArt();
        avt = new com.raven.swing.ImageAvatar();
        cmdSetCoverArt = new com.raven.component.OptionButton();
        information = new javax.swing.JLayeredPane();
        info1 = new javax.swing.JPanel();
        viewOutGender = new com.raven.component.ViewOut();
        viewOutDate = new com.raven.component.ViewOut();
        viewOutPhone = new com.raven.component.ViewOut();
        viewOutEmail = new com.raven.component.ViewOut();
        viewOutAddress = new com.raven.component.ViewOut();
        info2 = new javax.swing.JPanel();
        genderViewIn = new com.raven.component.GenderViewIn();
        dateViewIn = new com.raven.component.DateViewIn();
        viewPhone = new com.raven.component.ViewIn();
        viewEmail = new com.raven.component.ViewIn();
        viewAddress = new com.raven.component.ViewIn();
        lbError = new javax.swing.JLabel();
        viewUserName = new com.raven.component.ViewIn();
        title1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmdEditProfile = new com.raven.component.OptionButton();
        cmdEditProfileClose = new com.raven.component.OptionButton();
        cmdEditProfileOk = new com.raven.component.OptionButton();

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        body.setBackground(new java.awt.Color(255, 255, 255));

        title.setBackground(new java.awt.Color(255, 255, 255));
        title.setPreferredSize(new java.awt.Dimension(82, 25));
        title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                titleMousePressed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText(" Thông tin tài khoản:");

        cmdClose.setBackground(new java.awt.Color(218, 218, 218));
        cmdClose.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/close_panel.png"))); // NOI18N
        cmdClose.setPreferredSize(new java.awt.Dimension(25, 25));
        cmdClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdCloseMouseExited(evt);
            }
        });
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout titleLayout = new javax.swing.GroupLayout(title);
        title.setLayout(titleLayout);
        titleLayout.setHorizontalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        titleLayout.setVerticalGroup(
            titleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        scroll.setForeground(new java.awt.Color(255, 255, 255));
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        MenuList.setBackground(new java.awt.Color(255, 255, 255));
        MenuList.setForeground(new java.awt.Color(255, 255, 255));
        MenuList.setOpaque(true);

        layoutName.setLayout(new java.awt.CardLayout());

        panelName1.setBackground(new java.awt.Color(255, 255, 255));
        panelName1.setLayout(new javax.swing.BoxLayout(panelName1, javax.swing.BoxLayout.LINE_AXIS));

        lbSetName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lbSetName.setForeground(new java.awt.Color(0, 0, 0));
        lbSetName.setMaximumSize(new java.awt.Dimension(10000, 36));
        lbSetName.setPreferredSize(new java.awt.Dimension(100, 36));
        panelName1.add(lbSetName);

        cmdEditName.setBackground(new java.awt.Color(218, 218, 218));
        cmdEditName.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/pen.png"))); // NOI18N
        cmdEditName.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdEditName.setMinimumSize(new java.awt.Dimension(20, 20));
        cmdEditName.setPreferredSize(new java.awt.Dimension(20, 20));
        cmdEditName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditNameMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditNameMouseExited(evt);
            }
        });
        cmdEditName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditNameActionPerformed(evt);
            }
        });
        panelName1.add(cmdEditName);

        layoutName.add(panelName1, "card3");

        panelName2.setBackground(new java.awt.Color(255, 255, 255));
        panelName2.setLayout(new javax.swing.BoxLayout(panelName2, javax.swing.BoxLayout.X_AXIS));

        txtSetName.setMinimumSize(new java.awt.Dimension(200, 36));
        txtSetName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // txtSetNameMouseClicked(evt);
                //dcmm
            }
        });
        txtSetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // txtSetNameActionPerformed(evt);
            }
        });
        panelName2.add(txtSetName);

        cmdEditNameClose.setForeground(new java.awt.Color(218, 218, 218));
        cmdEditNameClose.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/delete_1.png"))); // NOI18N
        cmdEditNameClose.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdEditNameClose.setPreferredSize(new java.awt.Dimension(20, 36));
        cmdEditNameClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditNameCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditNameCloseMouseExited(evt);
            }
        });
        cmdEditNameClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditNameCloseActionPerformed(evt);
            }
        });
        panelName2.add(cmdEditNameClose);

        cmdEditNameOk.setBackground(new java.awt.Color(183, 232, 183));
        cmdEditNameOk.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/tick_ok.png"))); // NOI18N
        cmdEditNameOk.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdEditNameOk.setMinimumSize(new java.awt.Dimension(20, 20));
        cmdEditNameOk.setPreferredSize(new java.awt.Dimension(20, 20));
        cmdEditNameOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditNameOkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditNameOkMouseExited(evt);
            }
        });
        cmdEditNameOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditNameOkActionPerformed(evt);
            }
        });
        panelName2.add(cmdEditNameOk);

        layoutName.add(panelName2, "card2");

        coverArt.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/testing/dog.jpg"))); // NOI18N

        avt.setBorderColor(new java.awt.Color(255, 255, 255));
        avt.setBorderSize(4);
        avt.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/profile.png"))); // NOI18N
        avt.setPreferredSize(new java.awt.Dimension(100, 100));
        avt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                avtMouseClicked(evt);
            }
        });
        coverArt.add(avt);
        avt.setBounds(0, 100, 100, 100);

        cmdSetCoverArt.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/pictureSelected.png"))); // NOI18N
        cmdSetCoverArt.setMaximumSize(new java.awt.Dimension(30, 30));
        cmdSetCoverArt.setMinimumSize(new java.awt.Dimension(30, 30));
        cmdSetCoverArt.setPreferredSize(new java.awt.Dimension(30, 30));
        cmdSetCoverArt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSetCoverArtActionPerformed(evt);
            }
        });
        coverArt.add(cmdSetCoverArt);
        cmdSetCoverArt.setBounds(0, 0, 20, 20);

        information.setLayout(new java.awt.CardLayout());

        info1.setBackground(new java.awt.Color(255, 255, 255));
        info1.setLayout(new java.awt.GridLayout(10, 1));
        info1.add(viewOutGender);
        info1.add(viewOutDate);
        info1.add(viewOutPhone);
        info1.add(viewOutEmail);
        info1.add(viewOutAddress);

        information.add(info1, "card2");

        info2.setBackground(new java.awt.Color(255, 255, 255));
        info2.setLayout(new java.awt.GridLayout(10, 1));
        info2.add(genderViewIn);
        info2.add(dateViewIn);
        info2.add(viewPhone);
        info2.add(viewEmail);
        info2.add(viewAddress);

        lbError.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbError.setForeground(new java.awt.Color(255, 51, 51));
        lbError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbError.setText("Lỗi!");
        info2.add(lbError);
        info2.add(viewUserName);

        information.add(info2, "card3");

        title1.setBackground(new java.awt.Color(255, 255, 255));
        title1.setPreferredSize(new java.awt.Dimension(82, 25));
        title1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                title1MousePressed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText(" Thông tin cá nhân:");

        cmdEditProfile.setBackground(new java.awt.Color(218, 218, 218));
        cmdEditProfile.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/edit_profile.png"))); // NOI18N
        cmdEditProfile.setPreferredSize(new java.awt.Dimension(25, 25));
        cmdEditProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditProfileMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditProfileMouseExited(evt);
            }
        });
        cmdEditProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditProfileActionPerformed(evt);
            }
        });

        cmdEditProfileClose.setBackground(new java.awt.Color(218, 218, 218));
        cmdEditProfileClose.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/delete_1.png"))); // NOI18N
        cmdEditProfileClose.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdEditProfileClose.setPreferredSize(new java.awt.Dimension(20, 36));
        cmdEditProfileClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditProfileCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditProfileCloseMouseExited(evt);
            }
        });
        cmdEditProfileClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditProfileCloseActionPerformed(evt);
            }
        });

        cmdEditProfileOk.setBackground(new java.awt.Color(183, 232, 183));
        cmdEditProfileOk.setIconSelected(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/tick_ok.png"))); // NOI18N
        cmdEditProfileOk.setMaximumSize(new java.awt.Dimension(20, 20));
        cmdEditProfileOk.setMinimumSize(new java.awt.Dimension(20, 20));
        cmdEditProfileOk.setPreferredSize(new java.awt.Dimension(20, 20));
        cmdEditProfileOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cmdEditProfileOkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cmdEditProfileOkMouseExited(evt);
            }
        });
        cmdEditProfileOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditProfileOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout title1Layout = new javax.swing.GroupLayout(title1);
        title1.setLayout(title1Layout);
        title1Layout.setHorizontalGroup(
            title1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(title1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdEditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(cmdEditProfileClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(cmdEditProfileOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        title1Layout.setVerticalGroup(
            title1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdEditProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdEditProfileClose, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cmdEditProfileOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MenuList.setLayer(layoutName, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(coverArt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(information, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MenuList.setLayer(title1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout MenuListLayout = new javax.swing.GroupLayout(MenuList);
        MenuList.setLayout(MenuListLayout);
        MenuListLayout.setHorizontalGroup(
            MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuListLayout.createSequentialGroup()
                .addGroup(MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(information, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MenuListLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(coverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(203, 203, 203))
            .addGroup(MenuListLayout.createSequentialGroup()
                .addGroup(MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MenuListLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(layoutName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(title1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MenuListLayout.setVerticalGroup(
            MenuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuListLayout.createSequentialGroup()
                .addComponent(coverArt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(layoutName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(title1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(information, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scroll.setViewportView(MenuList);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 367, Short.MAX_VALUE)
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE))
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(title, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyLayout.createSequentialGroup()
                    .addGap(0, 29, Short.MAX_VALUE)
                    .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(bodyLayout.createSequentialGroup()
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(519, 519, 519)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(200, Short.MAX_VALUE)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cmdCloseActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        setVisible(false);
    }//GEN-LAST:event_formMousePressed

    private void titleMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleMousePressed

    }//GEN-LAST:event_titleMousePressed

    private void cmdEditProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditProfileActionPerformed
        
        info1.setVisible(false);
        info2.setVisible(true);
        //
        cmdEditProfile.setVisible(false);
        cmdEditProfileClose.setVisible(true);
        cmdEditProfileOk.setVisible(true);
    }//GEN-LAST:event_cmdEditProfileActionPerformed

    private void title1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_title1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_title1MousePressed

    private void cmdEditNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditNameActionPerformed
        txtSetName.setText(modelProfile.getName());
        panelName1.setVisible(false);
        panelName2.setVisible(true);
    }//GEN-LAST:event_cmdEditNameActionPerformed

    private void cmdEditNameCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditNameCloseActionPerformed
        panelName2.setVisible(false);
        panelName1.setVisible(true);
    }//GEN-LAST:event_cmdEditNameCloseActionPerformed

    private void cmdEditNameOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditNameOkActionPerformed
        //Kiểm tra và lấy input
        if (txtSetName.getText().equals("")) {
            txtSetName.grabFocus();
            return;
        }
        if (txtSetName.getText().equals(modelProfile.getName())) {
            txtSetName.grabFocus();
            return;
        }
        //Xử lí
        Model_Name_Update d = new Model_Name_Update();
        d.setUserID(modelProfile.getUserID());
        d.setName(txtSetName.getText());
        PublicEvent.getInstance().getEventProfile().updateName(d);
        
        panelName2.setVisible(false);
        panelName1.setVisible(true);
        lbSetName.setText(txtSetName.getText());
        modelProfile.setName(txtSetName.getText());
        PublicEvent.getInstance().getEventMain().updateProfile(modelProfile);
        PublicEvent.getInstance().getEventMain().setTitleName(txtSetName.getText());

    }//GEN-LAST:event_cmdEditNameOkActionPerformed
    

    private void cmdEditProfileCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditProfileCloseActionPerformed
        info2.setVisible(false);
        info1.setVisible(true);
        //
        cmdEditProfile.setVisible(true);
        cmdEditProfileClose.setVisible(false);
        cmdEditProfileOk.setVisible(false);

    }//GEN-LAST:event_cmdEditProfileCloseActionPerformed

    private void cmdEditProfileOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditProfileOkActionPerformed
        boolean b = checkDataUpdateProfile();
        if (!b) {
            return;
        }
        
        String userName = viewUserName.getTxtContent().getText();
        if (isBlankOrWhitespace(userName)) {
            viewUserName.getTxtContent().grabFocus();
            lbError.setVisible(true);
            lbError.setText("The input is empty!");
            return;
        }
        String gender = genderViewIn.getComboBoxGender().getSelectedItem().toString();
        String date = dateViewIn.getComboBoxYear().getSelectedItem().toString() + "-" + dateViewIn.getComboBoxMon().getSelectedItem().toString() + "-" + dateViewIn.getComBoxDay().getSelectedItem().toString();
        
        String phone = viewPhone.getTxtContent().getText();
        if (isBlankOrWhitespace(phone)) {
            viewPhone.getTxtContent().grabFocus();
            lbError.setVisible(true);
            lbError.setText("The input is empty!");
            return;
        }
        String email = viewEmail.getTxtContent().getText();
        String address = viewAddress.getTxtContent().getText();
        //
        Model_Profile_Update data = new Model_Profile_Update(modelProfile.getUserID(), userName, gender, phone, date, email, address);
        PublicEvent.getInstance().getEventProfile().updateProfile(data);
        //
        info2.setVisible(false);
        info1.setVisible(true);
        //
        cmdEditProfile.setVisible(true);
        cmdEditProfileClose.setVisible(false);
        cmdEditProfileOk.setVisible(false);
        modelProfile.setUserName(userName);
        modelProfile.setGender(gender);
        modelProfile.setDate(date);
        modelProfile.setEmail(email);
        modelProfile.setPhoneNumber(phone);
        modelProfile.setAddress(address);
        setDataView();
    }//GEN-LAST:event_cmdEditProfileOkActionPerformed

    private void avtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_avtMouseClicked
        if (modelProfile.getUserID() == Service.getInstance().getUser().getUserID()) {
            File selectedFile = selectedImage();
            if (selectedFile == null) {
                return;
            }
            String imageBase64 = null;
            byte[] fileData;
            try {
                fileData = readFileToByteArray(selectedFile);
                imageBase64 = Base64.getEncoder().encodeToString(fileData);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Model_Image_Update data = new Model_Image_Update();
            data.setUserID(modelProfile.getUserID());
            if (imageBase64 == null) {
                return;
            }
            data.setImageData(imageBase64);
            boolean b = PublicEvent.getInstance().getEventProfile().updateAavatar(data);
            if (b) {
                avt.setImage(PublicEvent.getInstance().getEventProfile().createImage(imageBase64));
                avt.repaint();
                modelProfile.setImage(imageBase64);
                PublicEvent.getInstance().getEventMain().updateProfile(modelProfile);
                //
            }
        }
    }//GEN-LAST:event_avtMouseClicked

    private void cmdSetCoverArtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSetCoverArtActionPerformed
        File selectedFile = selectedImage();
        if (selectedFile == null) {
            return;
        }
        String imageBase64 = null;
        try {
            byte[] fileData = readFileToByteArray(selectedFile);
            imageBase64 = Base64.getEncoder().encodeToString(fileData);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Model_Image_Update data = new Model_Image_Update();
        data.setUserID(modelProfile.getUserID());
        if (imageBase64 == null) {
            return;
        }
        data.setImageData(imageBase64);
        boolean b = PublicEvent.getInstance().getEventProfile().updateCoverArt(data);
        if (b) {
            coverArt.setImage(PublicEvent.getInstance().getEventProfile().createImage(imageBase64));
            modelProfile.setCoverArt(imageBase64);
            PublicEvent.getInstance().getEventMain().updateProfile(modelProfile);
        }
    }//GEN-LAST:event_cmdSetCoverArtActionPerformed

    private void cmdCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCloseMouseEntered
        cmdClose.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdCloseMouseEntered

    private void cmdCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdCloseMouseExited
        cmdClose.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdCloseMouseExited

    private void cmdEditNameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameMouseEntered
        cmdEditName.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditNameMouseEntered

    private void cmdEditNameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameMouseExited
        cmdEditName.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditNameMouseExited

    private void cmdEditNameCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameCloseMouseEntered
        cmdEditNameClose.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditNameCloseMouseEntered

    private void cmdEditNameCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameCloseMouseExited
        cmdEditNameClose.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditNameCloseMouseExited

    private void cmdEditNameOkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameOkMouseEntered
        cmdEditNameOk.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditNameOkMouseEntered

    private void cmdEditNameOkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditNameOkMouseExited
        cmdEditNameOk.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditNameOkMouseExited

    private void cmdEditProfileMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileMouseEntered
        cmdEditProfile.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditProfileMouseEntered

    private void cmdEditProfileMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileMouseExited
        cmdEditProfile.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditProfileMouseExited

    private void cmdEditProfileCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileCloseMouseEntered
        cmdEditProfileClose.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditProfileCloseMouseEntered

    private void cmdEditProfileCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileCloseMouseExited
        cmdEditProfileClose.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditProfileCloseMouseExited

    private void cmdEditProfileOkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileOkMouseEntered
        cmdEditProfileOk.setContentAreaFilled(true);
    }//GEN-LAST:event_cmdEditProfileOkMouseEntered

    private void cmdEditProfileOkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmdEditProfileOkMouseExited
        cmdEditProfileOk.setContentAreaFilled(false);
    }//GEN-LAST:event_cmdEditProfileOkMouseExited
    
    private File selectedImage() {
        JFileChooser ch = new JFileChooser();
        ch.setMultiSelectionEnabled(false); // Chỉ cho phép chọn một tệp duy nhất
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
            return ch.getSelectedFile();
        }
        return null;
    }
    
    private boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg") || name.endsWith(".gif");
    }
    
    private byte[] readFileToByteArray(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileData = new byte[(int) file.length()];
        fileInputStream.read(fileData);
        fileInputStream.close();
        return fileData;
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane MenuList;
    private com.raven.swing.ImageAvatar avt;
    private javax.swing.JPanel body;
    private com.raven.component.OptionButton cmdClose;
    private com.raven.component.OptionButton cmdEditName;
    private com.raven.component.OptionButton cmdEditNameClose;
    private com.raven.component.OptionButton cmdEditNameOk;
    private com.raven.component.OptionButton cmdEditProfile;
    private com.raven.component.OptionButton cmdEditProfileClose;
    private com.raven.component.OptionButton cmdEditProfileOk;
    private com.raven.component.OptionButton cmdSetCoverArt;
    private com.raven.swing.PictureCoverArt coverArt;
    private com.raven.component.DateViewIn dateViewIn;
    private com.raven.component.GenderViewIn genderViewIn;
    private javax.swing.JPanel info1;
    private javax.swing.JPanel info2;
    private javax.swing.JLayeredPane information;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane layoutName;
    private javax.swing.JLabel lbError;
    private javax.swing.JLabel lbSetName;
    private javax.swing.JPanel panelName1;
    private javax.swing.JPanel panelName2;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JPanel title;
    private javax.swing.JPanel title1;
    private javax.swing.JTextField txtSetName;
    private com.raven.component.ViewIn viewAddress;
    private com.raven.component.ViewIn viewEmail;
    private com.raven.component.ViewOut viewOutAddress;
    private com.raven.component.ViewOut viewOutDate;
    private com.raven.component.ViewOut viewOutEmail;
    private com.raven.component.ViewOut viewOutGender;
    private com.raven.component.ViewOut viewOutPhone;
    private com.raven.component.ViewIn viewPhone;
    private com.raven.component.ViewIn viewUserName;
    // End of variables declaration//GEN-END:variables
}
