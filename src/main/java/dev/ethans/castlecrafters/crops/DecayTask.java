package dev.ethans.castlecrafters.crops;

import dev.ethans.castlecrafters.FoodDash;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class DecayTask implements CropTask {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;

    private BukkitTask task;

    public DecayTask(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @Override
    public void run(Location location) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                Block block = location.getBlock();

                if (block.getType() != Material.FARMLAND) {
                    plugin.getLogger().info(block.getType().name());
                    cancel();
                    cropManager.getCropTasks().remove(location);
                    return;
                }

                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, plugin.getGeneralConfig().getDecayDuration().toSeconds());
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
