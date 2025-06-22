package dev.ethans.castlecrafters.config;

import dev.ethans.castlecrafters.CastleCrafters;
import org.bukkit.configuration.file.YamlConfiguration;

import java.time.Duration;

public class GeneralConfig implements FileConfig {

    private Duration gameDuration;
    private String map;

    @Override
    public CastleCrafters getPlugin() {
        return CastleCrafters.getInstance();
    }

    @Override
    public void load() {
        getPlugin().saveDefaultConfig();

        // Load game duration from config
        this.gameDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Game.Duration"));
        // Load map name from config
        this.map = getPlugin().getConfig().getString("Game.Map");
    }

    @Override
    public void save() {
        getPlugin().saveConfig();
    }

    @Override
    public void reload() {
        getPlugin().reloadConfig();

        // Load game duration from config
        this.gameDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Game.Duration"));
        // Load map name from config
        this.map = getPlugin().getConfig().getString("Game.Map");
    }

    @Override
    public YamlConfiguration getConfig() {
        return (YamlConfiguration) getPlugin().getConfig();
    }

    public Duration getGameDuration() {
        return gameDuration;
    }

    public String getMap() {
        return map;
    }
}
