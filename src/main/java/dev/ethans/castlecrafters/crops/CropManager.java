package dev.ethans.castlecrafters.crops;

import dev.ethans.castlecrafters.FoodDash;
import dev.ethans.castlecrafters.shop.ShopUpgrade;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class CropManager {

    public static final FoodDash foodDash = FoodDash.getInstance();

    private static long growDuration;
    private static Map<Location, BukkitTask> growTasks = new HashMap<>();

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

    public void addGrowTask(Location location) {

        if (growTasks.containsKey(location)) {
            growTasks.get(location).cancel();
            growTasks.remove(location);
        }

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Block block = location.getBlock();

                if (block.getType().isAir()) {
                    cancel();
                    growTasks.remove(location);
                    return;
                }

                if (!(block.getBlockData() instanceof Ageable ageable)) {
                    cancel();
                    growTasks.remove(location);
                    return;
                }

                ageable.setAge(ageable.getAge() + 1);
                block.setBlockData(ageable);

                if (ageable.getAge() == ageable.getMaximumAge()) {
                    cancel();
                    growTasks.remove(location);
                }
            }
        }.runTaskTimer(foodDash, growDuration, growDuration);
        growTasks.put(location, task);
    }
}
