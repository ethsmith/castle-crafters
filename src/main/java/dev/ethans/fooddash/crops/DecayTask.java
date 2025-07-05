package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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

                Block crop = location.clone().add(0, 1, 0).getBlock();
                crop.setType(Material.AIR);
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
