package dev.ethans.castlecrafters.wave;

import dev.ethans.castlecrafters.FoodDash;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveManager {

    private static final FoodDash plugin = FoodDash.getInstance();
    private static final List<Material> potentialItems = new ArrayList<>();

    private final int startingItemAmount = plugin.getGeneralConfig().getStartingItemAmount();

    private Wave currentWave;
    private long timeLeft;
    private BukkitTask timer;

    public WaveManager() {
        for (Material value : Material.values()) {
            if (value.asBlockType() == null) return;
            if (value.asBlockType().getBlockDataClass() != Ageable.class) return;
            potentialItems.add(value);
        }

        if (potentialItems.isEmpty()) {
            throw new IllegalStateException("Unable to find any potential items for Wave generation.");
        }

        currentWave = new Wave(1, new HashMap<>());
        generateWaveItems();
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(Wave currentWave) {
        this.currentWave = currentWave;
    }

    public void nextWave() {
        currentWave = new Wave(currentWave.waveNumber() + 1, new HashMap<>());
        generateWaveItems();
    }

    public void generateWaveItems() {
        int waveNumber = currentWave.waveNumber();
        Map<Material, Integer> items = currentWave.items();

        // Slowly increase the amount of items for each wave
        int amount = startingItemAmount + (waveNumber - 1) * 2;

        // Randomly select items from the potentialItems list
        for (int i = 0; i < amount; i++) {
            Material material = potentialItems.get((int) (Math.random() * potentialItems.size()));
            items.put(material, items.getOrDefault(material, 0) + 1);
        }
    }

    public void startTimer() {
        if (!timer.isCancelled()) return;

        timeLeft = plugin.getGeneralConfig().getWaveDuration().toSeconds();
        timer = plugin.getServer().getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                timeLeft--;
                if (timeLeft <= 0)
                    cancel();
            }
        }, 20L, 20L);
    }

    public void stopTimer() {
        if (timer.isCancelled()) return;
        timer.cancel();
    }

    public boolean timeIsUp() {
        return timeLeft <= 0;
    }
}
