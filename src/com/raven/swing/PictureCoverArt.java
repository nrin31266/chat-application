package com.raven.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

public class PictureCoverArt extends JLayeredPane {

    private Icon image;

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
        // Gọi repaint để vẽ lại khi hình ảnh thay đổi
        repaint();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); // Đảm bảo rằng các thành phần khác cũng được vẽ
        if (image != null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            Rectangle size = getAutoSize(image);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(toImage(image), size.x, size.y, size.width, size.height, null);
        }
    }

    private Rectangle getAutoSize(Icon image) {
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = image.getIconWidth();
        int imageHeight = image.getIconHeight();
        double xScale = (double) panelWidth / imageWidth;
        double yScale = (double) panelHeight / imageHeight;
        double scale = Math.max(xScale, yScale);  // Use Math.max to fill the entire panel
        int width = (int) (scale * imageWidth);
        int height = (int) (scale * imageHeight);
        int x = (panelWidth - width) / 2;
        int y = (panelHeight - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}
