package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JButton[][] gridButtons;
    private JLabel lblBalance;
    private JLabel lblCurrentWin;
    private JTextField txtBetAmount;
    private JTextField txtBombCount;
    private JButton btnStart;
    private JButton btnCashOut;
    private final int SIZE = 5;

    public GameFrame() {
        this.setTitle("Minestake Game");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        initTopPanel();
        initGridPanel();
        initControlPanel();
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        lblBalance = new JLabel("Balance: $0.0");
        lblBalance.setFont(new Font("Arial", Font.BOLD, 16));
        lblBalance.setHorizontalAlignment(SwingConstants.CENTER);

        lblCurrentWin = new JLabel("Current Win: $0.0");
        lblCurrentWin.setFont(new Font("Arial", Font.BOLD, 16));
        lblCurrentWin.setForeground(new Color(0, 150, 0));
        lblCurrentWin.setHorizontalAlignment(SwingConstants.CENTER);

        topPanel.add(lblBalance);
        topPanel.add(lblCurrentWin);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridButtons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton btn = new JButton();
                btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                btn.setFocusPainted(false);
                btn.setEnabled(false); // Disable lúc chưa start game
                gridButtons[i][j] = btn;
                gridPanel.add(btn);
            }
        }
        this.add(gridPanel, BorderLayout.CENTER);
    }

    private void initControlPanel() {
        JPanel controlPanel = new JPanel();

        controlPanel.add(new JLabel("Bet Amount:"));
        txtBetAmount = new JTextField("100", 5);

        controlPanel.add(new JLabel("Bombs (1-24):"));
        txtBombCount = new JTextField("3", 3);

        btnStart = new JButton("START GAME");
        btnStart.setBackground(new Color(50, 120, 200));
        btnStart.setForeground(Color.WHITE);

        btnCashOut = new JButton("CASH OUT");
        btnCashOut.setBackground(new Color(40, 160, 80));
        btnCashOut.setForeground(Color.WHITE);
        btnCashOut.setEnabled(false);

        controlPanel.add(txtBetAmount);
        controlPanel.add(txtBombCount);
        controlPanel.add(btnStart);
        controlPanel.add(btnCashOut);

        this.add(controlPanel, BorderLayout.SOUTH);
    }

    // --- Các phương thức để Controller tương tác ---

    public void updateBalance(double amount) {
        lblBalance.setText(String.format("Balance: $%.2f", amount));
    }

    public void updateCurrentWin(double amount) {
        lblCurrentWin.setText(String.format("Current Win: $%.2f", amount));
    }

    public String getBetAmount() { return txtBetAmount.getText(); }
    public String getBombCount() { return txtBombCount.getText(); }

    public void setGameActiveState(boolean isActive) {
        btnStart.setEnabled(!isActive);
        btnCashOut.setEnabled(isActive);
        txtBetAmount.setEnabled(!isActive);
        txtBombCount.setEnabled(!isActive);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j].setEnabled(isActive);
            }
        }
    }

    public void resetGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setBackground(null);
            }
        }
    }

    public void revealCell(int r, int c, boolean isBomb) {
        if (isBomb) {
            gridButtons[r][c].setText("💣");
            gridButtons[r][c].setBackground(Color.RED);
        } else {
            gridButtons[r][c].setText("💎");
            gridButtons[r][c].setBackground(Color.GREEN);
        }
        gridButtons[r][c].setEnabled(false);
    }

    // Dependency Injection cho Events
    public void addStartListener(ActionListener listener) { btnStart.addActionListener(listener); }
    public void addCashOutListener(ActionListener listener) { btnCashOut.addActionListener(listener); }

    public void addGridListener(int r, int c, ActionListener listener) {
        // Xóa listener cũ để tránh bị duplicate khi start game mới
        for(ActionListener al : gridButtons[r][c].getActionListeners()) {
            gridButtons[r][c].removeActionListener(al);
        }
        gridButtons[r][c].addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}