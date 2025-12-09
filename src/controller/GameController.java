package controller;

import logic.board;
import logic.square;
import logic.wallet;
import ui.GameFrame;
import ui.Theme;

import java.awt.*;
import java.awt.event.ActionEvent;

import static logic.payout.calculateMultiplier;


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

        //update initial balance
        view.updateBalance(wallet.getBalance());

        //add listeners to event
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

    private void resetGame() {
        // reset game state
        view.resetGrid();
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

        // Convert 2D coordinates to 1D index if needed by your board logic,
        // or keep using (r,c) if your board supports it.
        // Assuming board uses (r,c) or 1D index internally.
        int index = r * 5 + c;

        if (board.isRevealed(index)) return;

        boolean safe = board.revealSquare(index);

        if (safe) {
            view.revealCell(r, c, false);
            // WIN
            currentMultiplier = calculateMultiplier(bombCount, board.getTotalRevealed());
            view.updateCurrentWin(currentBet * currentMultiplier);
            if (board.getTotalRevealed() == 25 - bombCount) {
                cashOut(); // Auto cash out on full clear
            }
        } else {
            // LOSS
            view.revealCell(r, c, true);
            gameOver(false);
        }
    }


    private void cashOut() {
        if (!isGameActive) return;

        double winAmount = currentBet * currentMultiplier;
        wallet.add(winAmount);

        view.updateBalance(wallet.getBalance());

        // Hiện Popup Thắng
        view.showResultPopup(true, currentMultiplier, winAmount);

        gameOver(true);
    }

    private void gameOver(boolean win) {
        isGameActive = false;
        view.setGameActiveState(false);
        if (!win) {
            view.showResultPopup(false, 0.0, 0.0);
        }
        for (int i = 0; i < 25; i++) {
            if (board.isRevealed(i)) continue;

            if (board.isBomb(i)) {
                if (win) view.setTileState(i, "💣", Theme.TILE_DEFAULT, null);
                else view.setTileState(i, "💣", new Color(60, 30, 30), null);
            } else {
                view.setTileState(i, "💎", new Color(30, 40, 50), new Color(255, 255, 255, 100));
            }
        }
        view.setGameActiveState(false);
    }
}