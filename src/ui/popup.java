package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ui.Theme.*;

public class popup extends JDialog {
    public popup(JFrame owner, boolean isWin, double multiplier, double amount) {
        super(owner, true);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));

        Color accentColor = isWin ? Theme.ACCENT_GREEN : Theme.ACCENT_RED;
        String multiText = String.format("%.2fx", multiplier);
        String amountText = isWin ? String.format("+$%.2f", amount) : "Good luck next time!";

        RoundedPanel panel = new RoundedPanel();
        panel.setBackground(Theme.BG_PANEL);
        panel.setBorderColor(accentColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lblMulti = new JLabel(multiText);
        lblMulti.setFont(Theme.FONT_POPUP_BIG);
        lblMulti.setForeground(accentColor);
        lblMulti.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAmount = new JLabel(amountText);
        lblAmount.setFont(Theme.FONT_POPUP_SMALL);
        lblAmount.setForeground(accentColor);
        lblAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(lblMulti);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lblAmount);
        panel.add(Box.createVerticalGlue());

        add(panel);
        setSize(180, 100);
        setLocationRelativeTo(owner);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { dispose(); }
        });
    }
}