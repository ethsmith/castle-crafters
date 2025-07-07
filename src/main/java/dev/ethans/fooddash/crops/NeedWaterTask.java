package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class NeedWaterTask implements CropTask {

    private static final FoodDash plugin = FoodDash.getInstance();
    private BukkitTask task;

    @Override
    public void run(Crop crop) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
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
