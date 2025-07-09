package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
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
                if (!crop.isValid()) {
                    plugin.getLogger().warning(String.format("Crop at %s is not valid, cannot set watered to false.", crop.getCrop().getLocation()));
                    plugin.getLogger().warning("Removing crop from crops list.");
                    cropManager.getCrops().remove(crop);
                    return;
                }

                crop.getCrop().setType(Material.AIR);
                crop.getSoil().setType(Material.DIRT);
                cropManager.getCrops().remove(crop);
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
