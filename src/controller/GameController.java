package controller;

import logic.board;
import logic.square;
import logic.wallet;
import ui.GameFrame;
import java.awt.event.ActionEvent;

public class GameController {
    private GameFrame view;
    private wallet wallet;
    private board board;

    private double currentBet;
    private double currentMultiplier;
    private int bombCount;
    private boolean isGameActive;

    public GameController(GameFrame view) {
        this.view = view;
        this.wallet = wallet.getInstance();

        // Cập nhật số dư ban đầu lên view
        view.updateBalance(wallet.getBalance());

        // Gắn sự kiện cho nút Start và Cashout
        this.view.addStartListener(e -> startGame());
        this.view.addCashOutListener(e -> cashOut());
    }

    private void startGame() {
        try {
            double bet = Double.parseDouble(view.getBetAmount());
            int bombs = Integer.parseInt(view.getBombCount());

            if (bombs < 1 || bombs > 24) {
                view.showMessage("Bombs must be between 1 and 24!");
                return;
            }

            if (wallet.deduct(bet)) {
                // Setup Game State
                this.currentBet = bet;
                this.bombCount = bombs;
                this.currentMultiplier = 1.0;
                this.isGameActive = true;

                // Tạo Model mới
                this.board = new board(bombs);

                // Setup View
                view.updateBalance(wallet.getBalance());
                view.updateCurrentWin(bet); // Ban đầu cashout = tiền cược
                view.resetGrid();
                view.setGameActiveState(true);

                // Gắn sự kiện click cho từng ô
                setupGridListeners();

            } else {
                view.showMessage("Not enough money!");
            }
        } catch (NumberFormatException e) {
            view.showMessage("Invalid input! Please enter numbers.");
        }
    }

    private void setupGridListeners() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                final int r = i;
                final int c = j;
                view.addGridListener(r, c, (ActionEvent e) -> handleCellClick(r, c));
            }
        }
    }

    private void handleCellClick(int r, int c) {
        if (!isGameActive) return;

        square cell = board.getSquare(r, c);
        if (cell.isRevealed()) return;

        cell.reveal();

        if (cell.isBomb()) {
            // LOSS
            view.revealCell(r, c, true);
            gameOver(false);
        } else {
            // WIN (Diamond)
            view.revealCell(r, c, false);
            calculateMultiplier();
        }
    }

    private void calculateMultiplier() {
        // Công thức đơn giản: Multiplier tăng dựa trên số bom
        // (Đây là logic game basic, có thể thay đổi công thức phức tạp hơn)
        double increase = 1.0 + (bombCount * 0.05);
        currentMultiplier *= increase;

        double currentWin = currentBet * currentMultiplier;
        view.updateCurrentWin(currentWin);
    }

    private void cashOut() {
        if (!isGameActive) return;

        double winAmount = currentBet * currentMultiplier;
        wallet.add(winAmount);

        view.updateBalance(wallet.getBalance());
        view.showMessage("You cashed out: $" + String.format("%.2f", winAmount));

        gameOver(true);
    }

    private void gameOver(boolean win) {
        isGameActive = false;
        view.setGameActiveState(false);
        if (!win) {
            view.showMessage("BOOM! You lost $" + currentBet);
            view.updateCurrentWin(0);
        }
    }
}