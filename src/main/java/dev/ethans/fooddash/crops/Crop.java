package dev.ethans.fooddash.crops;

import com.maximde.hologramlib.hologram.TextHologram;
import dev.ethans.fooddash.FoodDash;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Crop {

    public static final List<ItemType> validCrops = List.of(
            ItemType.WHEAT,
            ItemType.CARROT,
            ItemType.POTATO
    );

    public static final FoodDash plugin = FoodDash.getInstance();

    private final CropManager cropManager;
    private final Block crop;
    private final Block soil;

    private boolean watered = false;

    private final AtomicInteger timeSpent = new AtomicInteger(0);
    private final TextHologram hologram;
    private final BukkitTask holoUpdater;

    public Crop(CropManager cropManager, Block crop, Block soil) {
        this.cropManager = cropManager;
        this.crop = crop;
        this.soil = soil;

        cropManager.getCrops().add(this);

        hologram = new TextHologram(toString())
                .setMiniMessageText("<aqua>" + plugin.getGeneralConfig().getOutOfWaterDuration().toSeconds() + "s")
                .setSeeThroughBlocks(false)
                .setShadow(true)
                .setScale(1.0f, 1.0f, 1.0f)
                .setTextOpacity((byte) 200)
                .setBackgroundColor(Color.fromARGB(60, 255, 236, 222).asARGB())
                .setMaxLineWidth(200);
        plugin.getHologramManager().spawn(hologram, crop.getLocation().add(0, 1, 0));

        holoUpdater = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isValid()) {
                    hologram.removeAllViewers();
                    cancel();
                    return;
                }

                int spent = timeSpent.incrementAndGet();
                hologram.setText("<aqua>" + (plugin.getGeneralConfig().getOutOfWaterDuration().toSeconds() - spent) + "s");
                hologram.update();
            }
        }.runTaskTimer(plugin, 0, 20);

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

        timeSpent.set(0);
        hologram.setText("<aqua>" + plugin.getGeneralConfig().getOutOfWaterDuration().toSeconds() + "s");
        hologram.update();
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

    public TextHologram getHologram() {
        return hologram;
    }

    public BukkitTask getHoloUpdater() {
        return holoUpdater;
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
