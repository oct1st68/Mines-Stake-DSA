package logic;

public class payout {
    public static double calculatePayout(int totalMines, int revealedSafeSquares) {
        int totalSquares = 25; // Assuming a 5x5 board
        int remainingCells = totalSquares - revealedSafeSquares;
        int remainingMines = totalSquares - totalMines - revealedSafeSquares;

        if(remainingCells <= 0) {
            return 0.0; // No remaining cells, no payout
        }

        double proba = (double) remainingMines / remainingCells;

        return 1.0/proba; // Payout is the inverse of the probability
    }

    public static double getCurrentPayout(int totalMines, int revealedSafeSquares) {
        double mul = 1.0;
        int totalSquares = 25; // Assuming a 5x5 board

        for(int i = 0; i < revealedSafeSquares; i++) {
            double proba = (double) (totalSquares - totalMines - i) / (totalSquares - i);
            mul *= 1.0/proba;
        }
        return mul;
    }
}
