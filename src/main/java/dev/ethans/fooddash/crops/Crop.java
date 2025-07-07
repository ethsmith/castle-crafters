package dev.ethans.fooddash.crops;

import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

public class Crop {

    private final Block crop;
    private final Block soil;

    private boolean watered = false;

    private CropTask needWaterTask;

    public Crop(Block crop, Block soil) {
        this.crop = crop;
        this.soil = soil;
    }

    public Block getCrop() {
        return crop;
    }

    public Block getSoil() {
        return soil;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
        if (needWaterTask == null) return;
        needWaterTask.cancel();
        needWaterTask = new NeedWaterTask();
    }
}
