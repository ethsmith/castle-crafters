package dev.ethans.fooddash.crops;

import org.bukkit.Location;

public interface CropTask {

    void run(Location location);

    boolean isCancelled();

    void cancel();
}
