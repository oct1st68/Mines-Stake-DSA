package logic;

import java.math.BigDecimal;
import java.math.RoundingMode;

class payout {
    private static final double RTP = 0.95;
    private static final int TOTAL_TILES = 25;

    public static double calculateMultiplier(int bombs, int gemsFound) {
        double multiplier = 1.0;
        for (int i = 0; i < gemsFound; i++) {
            double totalRemaining = TOTAL_TILES - i;
            double safeRemaining = TOTAL_TILES - bombs - i;
            multiplier *= (totalRemaining / safeRemaining);
        }
        multiplier = multiplier * RTP;
        return new BigDecimal(multiplier).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }
}