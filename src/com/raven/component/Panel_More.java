
package com.raven.component;

import com.raven.main.Main;
import com.raven.swing.WrapLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class Panel_More extends javax.swing.JPanel {

    public Panel_More() {
        initComponents();
        init();
    }
    private void init(){
        setLayout(new MigLayout("fillx"));
        panelHeader= new JPanel();
        panelHeader.add(getButtonFile());
        panelHeader.add(getEmojiStyle1());
        panelHeader.add(getEmojiStyle2());
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.LINE_AXIS));
        add(panelHeader, "w 100%, h 30!, wrap");
        panelDetail= new JPanel();
        JScrollPane ch= new  JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new JScrollBar());
        //test color
//        panelDetail.setBackground(Color.red);
        add(ch, "w 100%, h 100%");
        
        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));
        
        
    }
    private JButton getButtonFile(){
        OptionButton cmd= new  OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/raven/icon/link.png")));
        cmd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ch= new  JFileChooser();
                ch.showOpenDialog(Main.getFrames()[0]);
                //Cap nhap sau
            }
            
        });
        return cmd;
    }
    private JButton getEmojiStyle1(){
        OptionButton cmd= new  OptionButton();
        cmd.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/com/raven/emoji/icon/1.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

        return cmd;
    }
    private JButton getEmojiStyle2(){
        OptionButton cmd= new  OptionButton();
        cmd.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/com/raven/emoji/icon/2.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

        return cmd;
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
    private JPanel panelHeader;
    private JPanel panelDetail;
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
