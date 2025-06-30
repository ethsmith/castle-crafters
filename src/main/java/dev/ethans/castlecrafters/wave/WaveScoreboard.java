package dev.ethans.castlecrafters.wave;

import dev.ethans.castlecrafters.FoodDash;

public class WaveScoreboard {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final WaveManager waveManager;

    public WaveScoreboard(WaveManager waveManager) {
        this.waveManager = waveManager;
    }
}
