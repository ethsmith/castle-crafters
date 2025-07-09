package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class NeedWaterTask implements CropTask {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;
    private BukkitTask task;

    public NeedWaterTask(CropManager cropManager) {
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
                crop.setWatered(false);
            }
        }.runTaskLater(plugin, plugin.getGeneralConfig().getOutOfWaterDuration().toSeconds() * 20);
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
