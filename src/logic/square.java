package logic;

import java.util.*;

public class square {
    private int id;
    private boolean isBomb;
    private boolean isRevealed;

    public square(int id) {
        this.id = id;
        this.isBomb = isBomb;
        this.isRevealed = false;
    }

    public int getId() {
        return id;
    }

    public void setBombs(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public boolean isBomb() {
        return isBomb;
    }
    public boolean isRevealed() {
        return isRevealed;
    }
    public void reveal() {
        this.isRevealed = true;
    }

    public void reset(){
        this.isRevealed = false;
        this.isBomb = false;
    }

}
