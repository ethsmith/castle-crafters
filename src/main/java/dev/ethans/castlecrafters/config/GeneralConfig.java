package dev.ethans.castlecrafters.config;

import dev.ethans.castlecrafters.FoodDash;
import org.bukkit.configuration.file.YamlConfiguration;

import java.time.Duration;

public class GeneralConfig implements FileConfig {

    private Duration waveDuration;
    private Duration growDuration;
    private String map;
    private int startingItemAmount;

    @Override
    public FoodDash getPlugin() {
        return FoodDash.getInstance();
    }

    @Override
    public void load() {
        getPlugin().saveDefaultConfig();

        // Load wave duration from config
        this.waveDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Game.Wave_Duration"));
        this.growDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Crops.Grow_Duration"));
        // Load map name from config
        this.map = getPlugin().getConfig().getString("Game.Map");
        // Load starting item amount from config
        this.startingItemAmount = getPlugin().getConfig().getInt("Waves.Starting_Item_Amount");
    }

    @Override
    public void save() {
        getPlugin().saveConfig();
    }

    @Override
    public void reload() {
        getPlugin().reloadConfig();

        // Load game duration from config
        this.waveDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Game.Wave_Duration"));
        this.growDuration = Duration.ofSeconds(getPlugin().getConfig().getLong("Crops.Grow_Duration"));
        // Load map name from config
        this.map = getPlugin().getConfig().getString("Game.Map");
        // Load starting item amount from config
        this.startingItemAmount = getPlugin().getConfig().getInt("Waves.Starting_Item_Amount");
    }

    @Override
    public YamlConfiguration getConfig() {
        return (YamlConfiguration) getPlugin().getConfig();
    }

    public Duration getWaveDuration() {
        return waveDuration;
    }

    public Duration getGrowDuration() {
        return growDuration;
    }

    public int getStartingItemAmount() {
        return startingItemAmount;
    }

    public String getMap() {
        return map;
    }
}
