package dev.ethans.castlecrafters.crops;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;

public interface CropTask {

    void run(Location location);

    boolean isCancelled();

    void cancel();
}
