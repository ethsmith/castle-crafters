package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.crops.Crop;
import dev.ethans.fooddash.crops.CropManager;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CropPlaceListener implements Listener {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;

    public CropPlaceListener(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @EventHandler
    public void onCropPlace(BlockPlaceEvent event) {
        Block cropBlock= event.getBlockPlaced();
        Block soilBlock = cropBlock.getLocation().subtract(0, 1, 0).getBlock();
        if (!Crop.isValid(cropBlock, soilBlock)) return;
        new Crop(cropManager, cropBlock, soilBlock);
    }
}
