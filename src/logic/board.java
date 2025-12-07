package logic;

import java.util.*;

public class board {
    private List<square> squares;
    private int totalBombs;
    private int size = 5;

    private boolean isGameOver;
    private boolean isCashout;

    public board(int totalBombs) {
        this.totalBombs = totalBombs;
        initializeBoard();
        placeBombs();
    }

    private void initializeBoard(){
        squares = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            squares.add(new square(i));
        }
    }

    private void placeBombs() {
        this.isGameOver = false;
        this.isCashout = false;

        //reset
        for (square s : squares) s.reset();

        // --- ALGORITHM: Fisher-Yates Shuffle ---
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 25; i++) indexes.add(i);
        Collections.shuffle(indexes);

        for (int i = 0; i < totalBombs; i++) {
            squares.get(indexes.get(i)).setBombs(true);
        }
    }

    public square getSquare(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return squares.get(row * size + col);
        }
        return null;
    }

    public boolean revealSquare(int index) {
        square sq = squares.get(index);

        if (sq.isRevealed()) return true;

        sq.reveal();
//        moveHistory.push(index); // DSA: save Stack of moves

        if (sq.isBomb()) {
            this.isGameOver = true;
            return false; // Boom!
        }
        return true; // Safe
    }

    public int getSize() { return size; }
}

