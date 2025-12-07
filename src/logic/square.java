package logic;

import java.util.*;

public class square {
    private boolean isOpen;
    private boolean hasMine;
    private int neighborMines; // Number of neighboring mines
    private boolean isTarget;

    public square(){
        this.isOpen = false;
        this.hasMine = false;
        this.isTarget = false;
        this.neighborMines = 0;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }
    public boolean isHasMine() {
        return hasMine;
    }
    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }
    public int getNeighborMines() {
        return neighborMines;
    }
    public void setNeighborMines(int neighborMines) {
        this.neighborMines = neighborMines;
    }
    public boolean isTarget() {
        return isTarget;
    }
    public void setTarget(boolean isTarget) {
        this.isTarget = isTarget;
    }


}
