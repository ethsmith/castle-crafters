package dev.ethans.castlecrafters.wave;

import dev.ethans.castlecrafters.FoodDash;
import dev.ethans.castlecrafters.state.base.GameState;
import org.bukkit.inventory.ItemType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WaveManager {

    private static final FoodDash plugin = FoodDash.getInstance();
    private static final List<ItemType> potentialItems = List.of(
            ItemType.WHEAT,
            ItemType.CARROT,
            ItemType.POTATO,
            ItemType.MELON_SLICE,
            ItemType.PUMPKIN
    );

    private final int startingItemAmount = plugin.getGeneralConfig().getStartingItemAmount();
    private final GameState gameState;
    private final WaveTimer waveTimer;
    private final WaveScoreboard scoreboard;

    private Wave currentWave;
    private long timeLeft;
    private BukkitTask timer;

    public WaveManager(GameState gameState) {
        this.gameState = gameState;

        setCurrentWave(new Wave(1, new HashMap<>()));

        this.waveTimer = new WaveTimer(this);
        this.scoreboard = new WaveScoreboard(this);

        generateWaveItems();
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(Wave currentWave) {
        this.currentWave = currentWave;
    }

    public void nextWave() {
        setCurrentWave(new Wave(currentWave.waveNumber() + 1, new HashMap<>()));
        generateWaveItems();
    }

    public void generateWaveItems() {
        int waveNumber = currentWave.waveNumber();
        Map<ItemType, Integer> items = currentWave.items();

        // Slowly increase the amount of items for each wave
        int amount = startingItemAmount + (waveNumber - 1) * 2;

        int totalAmount = 0;
        while (totalAmount < amount) {
            ItemType type = potentialItems.get((int) (Math.random() * potentialItems.size()));
            int amountToAdd = new Random().nextInt(1, amount - totalAmount);
            totalAmount += amountToAdd;
            items.put(type, items.getOrDefault(type, 0) + amountToAdd);
        }
    }

    public void startTimer() {
        if (timer != null && !timer.isCancelled()) return;

        timeLeft = plugin.getGeneralConfig().getWaveDuration().toSeconds();
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                timeLeft--;
                waveTimer.update();
                if (timeLeft <= 0)
                    cancel();
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public void stopTimer() {
        if (timer.isCancelled()) return;
        timer.cancel();
    }

    public boolean timeIsUp() {
        return timeLeft <= 0;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public WaveTimer getWaveTimer() {
        return waveTimer;
    }

    public WaveScoreboard getWaveScoreboard() {
        return scoreboard;
    }

    public GameState getGameState() {
        return gameState;
    }
}
