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

public class PictureBox extends JLayeredPane {

    // Biến để lưu trữ hình ảnh
    private Icon image;

    // Phương thức để lấy hình ảnh
    public Icon getImage() {
        return image;
    }

    // Phương thức để đặt hình ảnh
    public void setImage(Icon image) {
        this.image = image;
    }

    // Ghi đè phương thức paintComponent để vẽ hình ảnh
    @Override
    protected void paintComponent(Graphics grphcs) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D) grphcs;
            // Lấy kích thước tự động của hình ảnh
            Rectangle size = getAutoSize(image);
            // Thiết lập chất lượng vẽ ảnh
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // Vẽ hình ảnh lên giao diện
            g2.drawImage(toImage(image), size.getLocation().x, size.getLocation().y, size.getSize().width, size.getSize().height, null);
        }
        super.paintComponent(grphcs);
    }

    // Phương thức để tính kích thước tự động của hình ảnh
    private Rectangle getAutoSize(Icon image) {
        int w = getWidth(); // chiều rộng của PictureBox
        int h = getHeight(); // chiều cao của PictureBox
        // Nếu chiều rộng PictureBox lớn hơn chiều rộng ảnh, đặt w bằng chiều rộng ảnh
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        // Nếu chiều cao PictureBox lớn hơn chiều cao ảnh, đặt h bằng chiều cao ảnh
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth(); // chiều rộng của ảnh
        int ih = image.getIconHeight(); // chiều cao của ảnh
        // Tính tỷ lệ thu nhỏ
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale); // chọn tỷ lệ nhỏ nhất để giữ nguyên tỷ lệ ảnh
        // Tính kích thước mới của ảnh
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        // Tính toán vị trí để căn giữa ảnh
        int x = getWidth() / 2 - (width / 2);
        int y = getHeight() / 2 - (height / 2);
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }

    // Phương thức chuyển đổi từ Icon sang Image
    private Image toImage(Icon icon) {
        return ((ImageIcon) icon).getImage();
    }
}
