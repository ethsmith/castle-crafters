package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GrowTask implements CropTask {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;

    private BukkitTask task;

    public GrowTask(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @Override
    public void run(Crop crop) {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                Location location = crop.getCrop().getLocation();
                Block block = crop.getCrop();

                if (block.getType().isAir()) {
                    cancel();
                    cropManager.getCropTasks().remove(crop);
                    return;
                }

                if (!(block.getBlockData() instanceof Ageable ageable)) {
                    cancel();
                    cropManager.getCropTasks().remove(crop);
                    return;
                }

                if (ageable.getAge() == ageable.getMaximumAge()) {
                    cancel();
                    cropManager.getCropTasks().remove(crop);
                    cropManager.addTask(crop, new DecayTask(cropManager));
                    return;
                }

                ageable.setAge(ageable.getAge() + 1);
                block.setBlockData(ageable);
            }
        }.runTaskTimer(plugin, CropManager.getGrowDuration(), CropManager.getGrowDuration());
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
