package dev.ethans.castlecrafters;

import org.bukkit.plugin.java.JavaPlugin;

public final class CastleCrafters extends JavaPlugin {

    private static CastleCrafters instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CastleCrafters getInstance() {
        return instance;
    }
}
