package com.raven.component;

import com.raven.swing.PictureBox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class OptionButton extends JButton {

    private PictureBox pictureBox;
    
    
    
    public OptionButton() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(null); // Bỏ đường viền
        setAlignmentX(Component.LEFT_ALIGNMENT);
        pictureBox = new PictureBox();
        add(pictureBox);
    }
    public void setIconSelected(Icon icon) {
        pictureBox.setImage(icon);;
    }

    @Override
    public void setSelected(boolean bln) {
        super.setSelected(bln);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        if (isSelected()) {
            grphcs.setColor(new Color(109, 206, 255));
            grphcs.fillRect(0, getHeight() - 3, getWidth(), getHeight());
        }
    }
    @Override
    public void doLayout() {
        super.doLayout();
        // Đặt kích thước và vị trí của PictureBox để nó chiếm toàn bộ nút
        pictureBox.setBounds(0, 0, getWidth(), getHeight());
    }
}
