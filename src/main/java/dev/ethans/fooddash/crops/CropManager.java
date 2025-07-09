package dev.ethans.fooddash.crops;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropManager {

    public static final FoodDash plugin = FoodDash.getInstance();
    private final List<Crop> crops = new ArrayList<>();
    private final Map<Crop, CropTask> cropTasks = new HashMap<>();

    // Leaving this here for now, but it's not used
    // because growing only happens after you water
    // the crops, and not automatically on a timer anymore.
//    public static long getGrowDuration() {
//        return (plugin.getGeneralConfig().getGrowDuration().getSeconds()
//                * 20) - ShopUpgrade.GROW_SPEED.getLevel() * 20L;
//    }

    public void addTask(Crop crop, CropTask task) {
        if (!crop.isValid()) {
            plugin.getLogger().warning(String.format("Crop at %s is not valid, cannot set watered to false.", crop.getCrop().getLocation()));
            plugin.getLogger().warning("Removing crop from crops list.");
            getCrops().remove(crop);
            return;
        }

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

    public List<Crop> getCrops() {
        return crops;
    }

    public Crop getCrop(Location location) {
        for (Crop crop : getCrops()) {
            if (!crop.getCrop().getLocation().equals(location) && !crop.getSoil().getLocation().equals(location)) continue;
            return crop;
        }
        return null;
    }
}
