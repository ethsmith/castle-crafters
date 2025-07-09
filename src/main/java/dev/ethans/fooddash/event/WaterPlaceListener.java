package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.crops.Crop;
import dev.ethans.fooddash.crops.CropManager;
import dev.ethans.fooddash.wave.WaveManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
        if (player.getEquipment().getItemInMainHand().getType() != Material.WATER_BUCKET) return;
        Block block = event.getClickedBlock();

        if (block == null) return;

        boolean isOnCrop = Crop.validCrops.contains(block.getType().asItemType());
        if (!isOnCrop && block.getType() != Material.FARMLAND) return;

        event.setCancelled(true);

        Location location = block.getLocation();
        if (isOnCrop)
            location = location.subtract(0, 1, 0);

        final Location loc = location;
        Crop crop = cropManager.getCrops().stream()
            .filter(c -> c.getSoil().getLocation().equals(loc))
            .findFirst().orElse(null);

        if (crop == null) return;

        crop.setWatered(true);
    }
}
