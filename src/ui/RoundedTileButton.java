package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class RoundedTileButton extends JButton {
    private Color bgColor;
    private Color hoverColor;
    private int radius = 10;

    public RoundedTileButton(String text, Color bg, Color hover) {
        super(text);
        this.bgColor = bg;
        this.hoverColor = hover;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE); // Mặc định chữ trắng
        setFont(Theme.FONT_BOLD);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if(isEnabled()) { setBackground(hoverColor); repaint(); }
            }
            public void mouseExited(MouseEvent e) {
                if(isEnabled()) { setBackground(bgColor); repaint(); }
            }
        });
    }

    public void setBgColor(Color color) { this.bgColor = color; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // --- XỬ LÝ MÀU NỀN ---
        if (!isEnabled()) {
            // Khi disable: Màu nền tối hơn/mờ đi
            g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 100));
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(bgColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // --- XỬ LÝ TEXT ---
        FontMetrics fm = g.getFontMetrics();
        Rectangle stringBounds = fm.getStringBounds(this.getText(), g).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();

        // Fix lỗi chữ bị xám khi disable:
        if (!isEnabled()) {
            g2.setColor(new Color(255, 255, 255, 120)); // Trắng mờ (Alpha 120)
        } else {
            g2.setColor(Color.WHITE); // Trắng rõ
        }

        g2.setFont(getFont());
        g2.drawString(getText(), textX, textY);
        g2.dispose();
    }
}