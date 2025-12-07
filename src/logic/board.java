package logic;

import java.util.*;

public class board {
    private square[][] squares;
    private int totalBombs;
    private int size = 5;

    public board(int totalBombs) {
        this.totalBombs = totalBombs;
        initializeBoard();
    }

    private void initializeBoard(){
        squares = new square[size][size];
        List<Boolean> bombs = new ArrayList<>();

        for(int i = 0; i < totalBombs; i++) {
            bombs.add(true);
        }
        for(int i = 0; i < size * size - totalBombs; i++) {
            bombs.add(false);
        }
        Collections.shuffle(bombs);

        int temp = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = new square(bombs.get(temp++));
            }
        }
    }

    public square getSquare(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            return squares[row][col];
        }
        return null;
    }

    public int getSize() { return size; }
}

