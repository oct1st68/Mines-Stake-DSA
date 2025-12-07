package logic;

import java.util.*;

public class board {
    private List<square> squares;
    private int totalBombs;
    private int size = 5;

    private Stack<Integer> moveHistory; // DSA: Stack to save moves

    private boolean isGameOver;
    private boolean isCashout;

    public board(int totalBombs) {
        this.totalBombs = totalBombs;
        this.squares = new ArrayList<>();
        this.moveHistory = new Stack<>();
        initializeBoard();
        placeBombs();
        printBoard();
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
        this.moveHistory.clear();

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
        int index = row * size + col;
        return squares.get(index);
    }

    public boolean revealSquare(int index) {
        square sq = squares.get(index);

        if (sq.isRevealed()) return true;

        sq.reveal();
        moveHistory.push(index);

        if (sq.isBomb()) {
            this.isGameOver = true;
            return false; // Boom!
        }
        return true; // Safe
    }

    public int getSize() { return size; }

    //print out for debugging
    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                square sq = getSquare(i, j);
                if (sq.isBomb()) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" O ");
                }
            }
            System.out.println();
        }
        System.out.println("----------------");
    }

    public boolean isGameOver() { return isGameOver; }
    public boolean isCashout() { return isCashout; }
    public void setCashout(boolean cashout) { isCashout = cashout; }
    public int getTotalBombs() { return totalBombs; }
    public Stack<Integer> getMoveHistory() { return moveHistory; }

    public boolean isBomb(int index){
        return squares.get(index).isBomb();
    }

    public boolean isRevealed(int index){
        return squares.get(index).isRevealed();
    }

    public int getTotalRevealed(){
        int count = 0;
        for (square sq : squares){
            if (sq.isRevealed() && !sq.isBomb()){
                count++;
            }
        }
        return count;
    }
}

