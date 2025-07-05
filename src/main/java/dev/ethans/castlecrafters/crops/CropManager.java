package dev.ethans.castlecrafters.crops;

import dev.ethans.castlecrafters.FoodDash;
import dev.ethans.castlecrafters.shop.ShopUpgrade;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class CropManager {

    public static final FoodDash foodDash = FoodDash.getInstance();
    private static final Map<Location, CropTask> cropTasks = new HashMap<>();

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

    public void addTask(Location location, CropTask task) {
        if (cropTasks.containsKey(location)) {
            cropTasks.get(location).cancel();
            cropTasks.remove(location);
        }
        cropTasks.put(location, task);
        task.run(location);
    }

    public Map<Location, CropTask> getCropTasks() {
        return cropTasks;
    }
}
