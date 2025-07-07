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
    public void run(Crop crop) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = crop.getCrop().getLocation();
                Block block = crop.getSoil();

                if (block.getType() != Material.FARMLAND) {
                    cancel();
                    cropManager.getCropTasks().remove(location);
                    return;
                }

                Block plant = crop.getCrop();
                plant.setType(Material.AIR);
                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, plugin.getGeneralConfig().getDecayDuration().toSeconds() * 20);
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
