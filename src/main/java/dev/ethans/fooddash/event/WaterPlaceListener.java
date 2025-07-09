package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.crops.Crop;
import dev.ethans.fooddash.crops.CropManager;
import dev.ethans.fooddash.wave.WaveManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WaterPlaceListener implements Listener {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final WaveManager waveManager;
    private final CropManager cropManager;

    public WaterPlaceListener(WaveManager waveManager, CropManager cropManager) {
        this.waveManager = waveManager;
        this.cropManager = cropManager;
    }

    @EventHandler
    public void onWaterPlace(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        ItemStack bucket = player.getInventory().getItemInMainHand();
        if (bucket.getType() != Material.WATER_BUCKET) return;
        Block block = event.getClickedBlock();

        if (block == null) return;

        Location location = block.getLocation();
        Crop crop = cropManager.getCrop(location);

        if (crop == null) return;
        if (!crop.isValid()) return;

        event.setCancelled(true);
        player.getWorld().playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 1);
        bucket.setType(Material.BUCKET);
        crop.setWatered(true);
    }
}
