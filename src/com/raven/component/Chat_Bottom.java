package com.raven.component;

import com.raven.app.MessageType;
import com.raven.event.PublicEvent;
import com.raven.model.Model_Send_Message;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.JIMSendTextPane;
import com.raven.swing.ScrollBar;
import io.socket.client.Ack;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import jdk.jfr.Event;
import net.miginfocom.swing.MigLayout;

public class Chat_Bottom extends javax.swing.JPanel {
    
    private Model_User_Account user;

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
        panelMore.setUser(user);
    }
    
    
    public Chat_Bottom() {
        initComponents();
        init();
    }

    private void init() {
        mig = new MigLayout("fillx, filly", "2[fill]0[]0[]2", "2[fill]2[0]");
        this.setLayout(mig);
        
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                refresh();
                if(ke.getKeyChar()==10&&ke.isControlDown()){
                    enventSend(txt);
                }
            }
        });
        txt.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.black);
        txt.setHintText("Write Message Here ...");
        
        scroll.setViewportView(txt);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ScrollBar sb = new ScrollBar();
        sb.setBackground(Color.white);
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "2[]10[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        panel.setBackground(Color.WHITE);
        JButton cmd = new JButton();
        cmd.setBorder(null);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/send.png")));
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                enventSend(txt);
            }
        });
        
        JButton cmdMore = new JButton();
        cmdMore.setBorder(null);
        cmdMore.setContentAreaFilled(false);
        cmdMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/more_disable.png")));
        cmdMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(panelMore.isVisible()){
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/more_disable.png")));
                    panelMore.setVisible(false);
                    mig.setComponentConstraints(panelMore, "dock south, h 0!");
                    revalidate();
                }else {
                    cmdMore.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/more.png")));
                    panelMore.setVisible(true);
                    mig.setComponentConstraints(panelMore, "dock south, h 170!");  
                }
            }
        });
        panel.add(cmdMore);
        panel.add(cmd);
        
        
        add(panel, "wrap");
        panelMore= new Panel_More();
        panelMore.setVisible(false);
        add(panelMore, "dock south, h 0!");// dat chieu cao la 0
    }

    private void send(Model_Send_Message data){
        Service.getInstance().getClient().emit("send_to_user", data.toJsonObject(),new Ack(){
            @Override
            public void call(Object... os) {
                System.err.println("Nhan dc tin nhan tu server");
//                String txt=(String) os[1];
//                System.err.println(txt);
            }
            
        });
    }
    private void refresh() {
        revalidate();
    }
    private void enventSend(JIMSendTextPane txt){
        System.out.println("Bottom select id: "+user.getUserID());
                String text = txt.getText().trim();
                if (!text.equals("")) {
                    Model_Send_Message message= new Model_Send_Message(MessageType.TEXT,Service.getInstance().getUser().getUserID(), user.getUserID(), text); 
                    System.out.println("Noi dung txt gui: "+message.toString());
                    
                    PublicEvent.getInstance().getEventChat().sendMessage(message);
                    send(message);
                    txt.setText("");
                    txt.grabFocus();
                    refresh();
                } else {
                    txt.grabFocus();
                }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private Panel_More panelMore;
    private MigLayout mig;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
