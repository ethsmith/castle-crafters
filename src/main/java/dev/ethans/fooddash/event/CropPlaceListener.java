package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.crops.Crop;
import dev.ethans.fooddash.crops.CropManager;
import dev.ethans.fooddash.crops.GrowTask;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CropPlaceListener implements Listener {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;

    public CropPlaceListener(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    // I am aware this doesn't account for natural growth of crops
    // but the game timer generally wouldn't ever be long enough
    // for crops to naturally grow
    @EventHandler
    public void onCropPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (!(block.getBlockData() instanceof Ageable)) return;
        Crop crop = new Crop(cropManager, block, block.getLocation().subtract(0, 1, 0).getBlock());
        cropManager.addTask(crop, new GrowTask(cropManager));
    }
}
