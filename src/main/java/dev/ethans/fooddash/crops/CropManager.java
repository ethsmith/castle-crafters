package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.shop.ShopUpgrade;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class CropManager {

    public static final FoodDash foodDash = FoodDash.getInstance();
    private static final Map<Crop, CropTask> cropTasks = new HashMap<>();

    private static long growDuration;

    public CropManager() {
        updateGrowDuration();
    }

    public static void updateGrowDuration() {
        growDuration = (foodDash.getGeneralConfig().getGrowDuration().getSeconds()
                * 20) - ShopUpgrade.GROW_SPEED.getLevel() * 20L;
    }

    public static long getGrowDuration() {
        return growDuration;
    }

    public void addTask(Crop crop, CropTask task) {
        if (cropTasks.containsKey(crop)) {
            cropTasks.get(crop).cancel();
            cropTasks.remove(crop);
        }
        cropTasks.put(crop, task);
        task.run(crop);
    }

    public Map<Crop, CropTask> getCropTasks() {
        return cropTasks;
    }
}
