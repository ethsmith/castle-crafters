package dev.ethans.castlecrafters.crops;

import dev.ethans.castlecrafters.FoodDash;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class DecayTask implements CropTask {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final CropManager cropManager;

    private BukkitTask task;

    public DecayTask(CropManager cropManager) {
        this.cropManager = cropManager;
    }

    @Override
    public void run(Location location) {
        Location center = location.clone().add(0.5, 2.0, 0.5); // Centered above block
        Color gray = Color.fromRGB(128, 128, 128);
        Particle.DustOptions dustOptions = new Particle.DustOptions(gray, 1.0F);

        AtomicInteger tick = new AtomicInteger(0);
        new BukkitRunnable() {
            @Override
            public void run() {
                center.getWorld().spawnParticle(Particle.DUST, center, 20, 0.2, 0, 0.2, 0, dustOptions);
                if (tick.getAndIncrement() > 100) cancel();
            }
        }.runTaskTimer(plugin, 0, 1);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                Block block = location.getBlock();

                if (block.getType() != Material.FARMLAND) {
                    plugin.getLogger().info(block.getType().name());
                    cancel();
                    cropManager.getCropTasks().remove(location);
                    return;
                }

                block.setType(Material.DIRT);
            }
        }.runTaskLater(plugin, plugin.getGeneralConfig().getDecayDuration().toSeconds());
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public void cancel() {
        task.cancel();
    }
}
