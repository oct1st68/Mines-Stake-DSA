package logic;

import java.util.*;

public class square {
    private boolean isBomb;
    private boolean isRevealed;

    public square(boolean isBomb) {
        this.isBomb = isBomb;
        this.isRevealed = false;
    }

    public boolean isBomb() {
        return isBomb;
    }
    public boolean isRevealed() {
        return isRevealed;
    }
    public void reveal() {
        isRevealed = true;
    }


}
