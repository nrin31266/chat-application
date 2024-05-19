package com.raven.component;

import com.raven.swing.PictureBox;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class MenuButton extends JButton {

    private Icon iconSimple;
    private Icon iconSelected;
    private PictureBox pictureBox;

    public MenuButton() {
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pictureBox = new PictureBox();
        add(pictureBox);
    }

    public Icon getIconSimple() {
        return iconSimple;
    }

    public void setIconSimple(Icon iconSimple) {
        this.iconSimple = iconSimple;
        if (!isSelected()) {
            pictureBox.setImage(iconSimple);
        }
    }

    public Icon getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(Icon iconSelected) {
        this.iconSelected = iconSelected;
        if (isSelected()) {
            pictureBox.setImage(iconSelected);
        }
    }

    @Override
    public void setSelected(boolean bln) {
        super.setSelected(bln);
        if (bln) {
            pictureBox.setImage(iconSelected);
        } else {
            pictureBox.setImage(iconSimple);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        if (isSelected()) {
            grphcs.setColor(new Color(190, 217, 240));
            grphcs.fillRect(0, getHeight() - 3, getWidth(), 3); // Chỉ vẽ một dải màu nhỏ phía dưới
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        // Đặt kích thước và vị trí của PictureBox để nó chiếm toàn bộ nút
        pictureBox.setBounds(0, 0, getWidth(), getHeight());
    }
}
