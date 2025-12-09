package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

import static ui.Theme.*;
import ui.*;

public class GameFrame extends JFrame {
    private JPanel gridPanel;
    private JPanel gridWrapper;
    private JButton[][] gridButtons;
    private JPanel glassPane;
    private JLabel lblBalance;
    private JLabel lblCurrentWin;
    private JTextField txtBetAmount;
    private JButton btnStart;
    private JButton btnCashOut;
    private JComboBox<Integer> cbBombs;
    private final int SIZE = 5;


    public GameFrame() {
        this.setTitle("MineStake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(BG_DARK);

        //try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //} catch (Exception ignored) {}
        setupGlassPane();
        initTopPanel();
        initCenterLayout();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                rescaleGrid();
            }
        });
    }

    private void rescaleGrid() {
        if (gridPanel == null || gridWrapper == null) return;

        int w = gridWrapper.getWidth();
        int h = gridWrapper.getHeight();
        if (w <= 0 || h <= 0) return;

        int side = Math.min(w, h);
        gridPanel.setPreferredSize(new Dimension(side, side));

        int cellSize = side / SIZE;
        int fontSize = (int)(cellSize * 0.45);
        if (fontSize < 8) fontSize = 8;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton btn = gridButtons[i][j];
                btn.setFont(btn.getFont().deriveFont((float) fontSize));
            }
        }

        gridWrapper.revalidate();
        gridWrapper.repaint();
    }


    private void addTileHoverEffect(JButton btn) {
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color hoverColor = TILE_BORDER.brighter();
            Color normalColor = TILE_IDLE;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.isEnabled() && btn.getText().isEmpty()) {
                    btn.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn.isEnabled() && btn.getText().isEmpty()) {
                    btn.setBackground(normalColor);
                }
            }
        });
    }

    private void addButtonHoverEffect(JButton btn, Color baseColor) {
        Color hoverColor = baseColor.brighter();

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(16, 24, 8, 24));
        topPanel.setBackground(BG_DARK);

//        JLabel title = new JLabel("Mines – Stake-style Demo");
//        title.setForeground(TEXT_PRIMARY);
//        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel balancePanel = new JPanel(new GridLayout(1, 2, 12, 0));
        balancePanel.setOpaque(false);


        lblBalance = new JLabel("Balance: $0.00");
        lblBalance.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblBalance.setForeground(TEXT_PRIMARY);
        lblBalance.setHorizontalAlignment(SwingConstants.RIGHT);

        lblCurrentWin = new JLabel("Current Win: $0.00");
        lblCurrentWin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCurrentWin.setForeground(ACCENT_GREEN);
        lblCurrentWin.setHorizontalAlignment(SwingConstants.RIGHT);

        balancePanel.add(lblBalance);
        balancePanel.add(lblCurrentWin);

        //topPanel.add(title, BorderLayout.WEST);
        topPanel.add(balancePanel, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterLayout() {
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(8, 24, 24, 24));

        gridWrapper = new JPanel(new GridBagLayout());
        gridWrapper.setOpaque(false);
        gridWrapper.add(initGridPanel());
        center.add(gridWrapper, BorderLayout.CENTER);

        center.add(initControlPanel(), BorderLayout.EAST);

        this.add(center, BorderLayout.CENTER);

        gridWrapper.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                rescaleGrid();
            }
        });
    }


    private JPanel initGridPanel() {
        gridPanel = new JPanel(new GridLayout(SIZE, SIZE, 8, 8));
        gridPanel.setBackground(BG_DARK);
        gridPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        gridButtons = new JButton[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                RoundedTileButton btn = new RoundedTileButton();
                btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(TILE_BORDER));
                btn.setBackground(TILE_IDLE);
                btn.setForeground(TEXT_PRIMARY);
                btn.setEnabled(false);
                btn.setMargin(new Insets(0, 0, 0, 0));
                addTileHoverEffect(btn);
                gridButtons[i][j] = btn;
                gridPanel.add(btn);
            }
        }
        return gridPanel;
    }


    private JPanel initControlPanel() {
        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        card.setPreferredSize(new Dimension(260, 280));

        JLabel lblBetTitle = new JLabel("Bet Settings");
        lblBetTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBetTitle.setForeground(TEXT_PRIMARY);

        JLabel lblBetAmount = new JLabel("Bet amount");
        lblBetAmount.setForeground(TEXT_MUTED);

        //size of bet amount text field
        txtBetAmount = new JTextField("100", 5);
        styleTextField(txtBetAmount);
        txtBetAmount.setMaximumSize(new Dimension(120, 20));
        txtBetAmount.setPreferredSize(new Dimension(120, 20));
        txtBetAmount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBombs = new JLabel("Bombs (1–24)");
        lblBombs.setForeground(TEXT_MUTED);

        Integer[] minesOpt = new Integer[24];
        for(int i=0; i<24; i++) minesOpt[i] = i;
        cbBombs = new JComboBox<>(minesOpt);
        cbBombs.setSelectedIndex(3); // Default 3 mines
        styleComboBox(cbBombs);
        card.add(cbBombs);
        card.add(Box.createVerticalStrut(20));

        btnStart = new JButton("Bet");
        stylePrimaryButton(btnStart, ACCENT_BLUE);
        addButtonHoverEffect(btnStart, ACCENT_BLUE);

        btnCashOut = new JButton("Cash out");
        stylePrimaryButton(btnCashOut, ACCENT_GREEN);
        addButtonHoverEffect(btnCashOut, ACCENT_GREEN);
        btnCashOut.setEnabled(false);

        card.add(lblBetTitle);
        card.add(Box.createVerticalStrut(16));

        card.add(lblBetAmount);
        card.add(Box.createVerticalStrut(4));
        card.add(txtBetAmount);
        card.add(Box.createVerticalStrut(12));

        card.add(lblBombs);
        card.add(Box.createVerticalStrut(4));
        card.add(cbBombs);
        card.add(Box.createVerticalStrut(16));

        card.add(btnStart);
        card.add(Box.createVerticalStrut(8));
        card.add(btnCashOut);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private void styleComboBox(JComboBox box) {
        box.setMaximumSize(new Dimension(300, 40));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBackground(Theme.INPUT_BG);
        box.setForeground(Color.WHITE);
    }

    private void stylePrimaryButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    private void styleTextField(JTextField tf) {
        tf.setBackground(new Color(15, 23, 42));
        tf.setForeground(TEXT_PRIMARY);
        tf.setCaretColor(TEXT_PRIMARY);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER),
                new EmptyBorder(4, 8, 4, 8)
        ));
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    public void updateBalance(double amount) {
        lblBalance.setText(String.format("Balance: $%.2f", amount));
    }

    public void updateCurrentWin(double amount) {
        lblCurrentWin.setText(String.format("Current Win: $%.2f", amount));
    }

    public String getBetAmount() { return txtBetAmount.getText(); }
    public int getBombCount() { return (Integer) cbBombs.getSelectedIndex(); }

    public void setGameActiveState(boolean isActive) {
        btnStart.setEnabled(!isActive);
        btnCashOut.setEnabled(isActive);
        txtBetAmount.setEnabled(!isActive);
        cbBombs.setEnabled(isActive);
        //txtBombCount.setEnabled(!isActive);

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
                gridButtons[i][j].setBackground(TILE_IDLE);
            }
        }
    }

    public void revealCell(int r, int c, boolean isBomb) {
        if (isBomb) {
            gridButtons[r][c].setText("💣");
            gridButtons[r][c].setBackground(new Color(220, 38, 38));
        } else {
            gridButtons[r][c].setText("💎");
            gridButtons[r][c].setBackground(new Color(22, 163, 74)); 
        }
        gridButtons[r][c].setEnabled(false);
    }

    public void showResultPopup(boolean win, double multiplier, double amount) {
        setGridBlur(true);
        popup popup = new popup(this, win, multiplier, amount);
        popup.setVisible(true);
        setGridBlur(false);
    }

    private void setupGlassPane() {
        glassPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();

                // Create blur effect
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Dark semi-transparent overlay simulating blur
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };
        glassPane.setOpaque(false);
        glassPane.setVisible(false);
        glassPane.addMouseListener(new java.awt.event.MouseAdapter() {}); // Block clicks
        setGlassPane(glassPane);
    }

    public void setGridBlur(boolean blur) {
        glassPane.setVisible(blur);
    }

    public void setTileState(int index, String text, Color bg, Color fg) {
        RoundedTileButton btn = (RoundedTileButton) gridButtons[index/5][index%5];
        btn.setText(text);
        if (fg != null) btn.setForeground(fg);
        btn.setEnabled(false);
    }

    public void addStartListener(ActionListener listener) { btnStart.addActionListener(listener); }
    public void addCashOutListener(ActionListener listener) { btnCashOut.addActionListener(listener); }

    public void addGridListener(int r, int c, ActionListener listener) {
        for (ActionListener al : gridButtons[r][c].getActionListeners()) {
            gridButtons[r][c].removeActionListener(al);
        }
        gridButtons[r][c].addActionListener(listener);
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
