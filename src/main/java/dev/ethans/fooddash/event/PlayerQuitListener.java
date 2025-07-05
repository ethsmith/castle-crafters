package dev.ethans.fooddash.event;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private static final FoodDash plugin = FoodDash.getInstance();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!plugin.getServer().getOnlinePlayers().isEmpty()) return;
        plugin.getServer().shutdown();
    }
}
