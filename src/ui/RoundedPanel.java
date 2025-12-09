package ui;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int radius = 15;
    private Color borderColor = null;

    public RoundedPanel() { setOpaque(false); }

    public void setBorderColor(Color c) { this.borderColor = c; repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        if (borderColor != null) {
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, radius, radius);
        }
        g2.dispose();
        super.paintComponent(g);
    }
}