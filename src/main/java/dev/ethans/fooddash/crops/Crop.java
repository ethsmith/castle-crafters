package dev.ethans.fooddash.crops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemType;

import java.util.List;

public class Crop {

    public static final List<ItemType> validCrops = List.of(
            ItemType.WHEAT,
            ItemType.CARROT,
            ItemType.POTATO
    );

    private final CropManager cropManager;
    private final Block crop;
    private final Block soil;

    private boolean watered = false;

    public Crop(CropManager cropManager, Block crop, Block soil) {
        this.cropManager = cropManager;
        this.crop = crop;
        this.soil = soil;

        cropManager.getCrops().add(this);
        setWatered(true);
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
        cropManager.addTask(this, new NeedWaterTask(cropManager));
    }

    public void grow() {

        if (crop.getType().isAir()) return;
        if (!(crop.getBlockData() instanceof Ageable ageable)) return;
        if (ageable.getAge() >= ageable.getMaximumAge()) return;

        ageable.setAge(ageable.getAge() + 1);
        crop.setBlockData(ageable);
    }

    public boolean isValid() {
        boolean validCrop = validCrops.contains(crop.getType().asItemType())
                && crop.getBlockData() instanceof Ageable;
        boolean validSoil = soil.getType() == Material.FARMLAND;
        return validCrop && validSoil;
    }

    public static boolean isValid(Block crop, Block soil) {
        boolean validCrop = validCrops.contains(crop.getType().asItemType())
                && crop.getBlockData() instanceof Ageable;
        boolean validSoil = soil.getType() == Material.FARMLAND;
        return validCrop && validSoil;
    }
}
