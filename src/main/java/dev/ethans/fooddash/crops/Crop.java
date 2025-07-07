package dev.ethans.fooddash.crops;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;

public class Crop {

    private CropManager cropManager;
    private final Block crop;
    private final Block soil;

    private boolean watered = false;

    private CropTask needWaterTask;

    public Crop(CropManager cropManager, Block crop, Block soil) {
        this.cropManager = cropManager;
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

        if (!watered) {
            DecayTask decayTask = new DecayTask(cropManager);
            cropManager.addTask(this, decayTask);
            return;
        }

        grow();
        needWaterTask = new NeedWaterTask();
        cropManager.addTask(this, needWaterTask);
    }

    public void grow() {
        Block crop = this.crop.getLocation().getBlock();

        if (crop.getType().isAir()) return;
        if (!(crop.getBlockData() instanceof Ageable ageable)) return;
        if (ageable.getAge() >= ageable.getMaximumAge()) return;

        ageable.setAge(ageable.getAge() + 1);
        crop.setBlockData(ageable);
    }
}
