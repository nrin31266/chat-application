package com.raven.component;

import com.raven.app.MessageType;
import com.raven.emoji.Emoji;
import com.raven.event.EventBody;
import com.raven.event.PublicEvent;
import com.raven.model.Model_HistoryChat;
import com.raven.model.Model_Login;
import com.raven.model.Model_Receive_Message;
import com.raven.model.Model_Send_Message;
import com.raven.model.Model_User_Account;
import com.raven.service.Service;
import com.raven.swing.ScrollBar;
import io.socket.client.Ack;
import io.socket.client.Socket;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

public class Chat_Body extends javax.swing.JPanel {

    private Model_User_Account user;

    public Model_User_Account getUser() {
        return user;
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
    }
    
    public Chat_Body() {
        initComponents();
        init();
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
        PublicEvent.getInstance().addEventBody(new EventBody(){
            @Override
            public void sendChatToHistoryChat(Model_HistoryChat data) {
                Service.getInstance().getClient().emit("history", data.toJsonObject());
            }
            
        });

    }

    public void addItemLeft(Model_Receive_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_Left item = new Chat_Left();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
            //  ::80% set max with 80%
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_Left item = new Chat_Left();
            item.setEmoji(Emoji.getInstance().getImoji(Integer.valueOf(data.getText())).getIcon());

            body.add(item, "wrap, w 5::80%");
            //  ::80% set max with 80%
            item.setTime();
        }else if(data.getMessageType()==MessageType.IMAGE){
            Chat_Left item = new Chat_Left();
            item.setText("");
            System.out.println("Huhu "+data.getDataImage());
            item.setImage(data.getDataImage());

            body.add(item, "wrap, w 5::80%");
            //  ::80% set max with 80%
            item.setTime();
        }else if(data.getMessageType()==MessageType.FILE){
            Chat_Left item = new Chat_Left();
            item.setText("");
            item.setFile(data.getDataFile());
            body.add(item, "wrap, w 5::80%");
            item.setTime();
        }
        repaint();
        revalidate();

        //sp to bottom
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = sp.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    public void addItemLeft(String text, String user, String[] image) {
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }

    public void addItemFile(String text, String user, String fileName, String fileSize) {
        Chat_Left_With_Profile item = new Chat_Left_With_Profile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(Model_Send_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_Right item = new Chat_Right();
            item.setText(data.getText());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
            //
            Model_HistoryChat dataHs=new Model_HistoryChat(Service.getInstance().getUser().getUserID(), user.getUserID(), 1, data.getText(), "", "");
            PublicEvent.getInstance().getEventBody().sendChatToHistoryChat(dataHs);
            
            
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_Right item = new Chat_Right();
            item.setEmoji(Emoji.getInstance().getImoji(Integer.valueOf(data.getText())).getIcon());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();//
            Model_HistoryChat dataHs=new Model_HistoryChat(Service.getInstance().getUser().getUserID(), user.getUserID(), 2, data.getText(), "","");
            PublicEvent.getInstance().getEventBody().sendChatToHistoryChat(dataHs);
            
        } else if (data.getMessageType() == MessageType.IMAGE) {
            Chat_Right item = new Chat_Right();
            item.setText("");
            item.setImage(data.getFile());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
            //
            Model_HistoryChat dataHs=new Model_HistoryChat(Service.getInstance().getUser().getUserID(), user.getUserID(), 3,"",data.getFile().getFile().getAbsolutePath(), "client_data/"+data.getFile().getFileID()+data.getFile().getFileExtensions());
            PublicEvent.getInstance().getEventBody().sendChatToHistoryChat(dataHs);
        }else if(data.getMessageType()==MessageType.FILE){
            Chat_Right item = new Chat_Right();
            item.setText("");
            item.setFile("file name", "file size", data.getFile());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
             //
            Model_HistoryChat dataHs=new Model_HistoryChat(Service.getInstance().getUser().getUserID(), user.getUserID(), 4,"",data.getFile().getFile().getAbsolutePath(), "client_data/"+data.getFile().getFileID()+data.getFile().getFileExtensions());
            PublicEvent.getInstance().getEventBody().sendChatToHistoryChat(dataHs);
        }
        repaint();
        revalidate();
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = sp.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }


    public void addDate(String date) {
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    public void clearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
