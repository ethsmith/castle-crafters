package dev.ethans.castlecrafters.wave;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class WaveTimer {

    private final BossBar timerBar;
    private final WaveManager waveManager;
    private final long startingTime;

    public WaveTimer(WaveManager waveManager) {
        this.waveManager = waveManager;
        this.startingTime = waveManager.getTimeLeft();

        timerBar = BossBar.bossBar(Component.text("Round " + waveManager.getCurrentWave().waveNumber() + ": "
                        + getFormattedTime(startingTime), NamedTextColor.BLUE),
                1, BossBar.Color.BLUE, BossBar.Overlay.PROGRESS);

        waveManager.getGameState().getPlayers().forEach(timerBar::addViewer);
    }

    private String getFormattedTime(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    public void update() {
        float progress = Math.min(1.0f, (float) waveManager.getTimeLeft() / startingTime);
        timerBar.progress(progress);
        timerBar.name(Component.text("Round " + waveManager.getCurrentWave().waveNumber() + ": "
                + getFormattedTime(waveManager.getTimeLeft()), NamedTextColor.BLUE));
    }

    public void removeAllPlayers() {
        waveManager.getGameState().getPlayers().forEach(timerBar::removeViewer);
    }
}
