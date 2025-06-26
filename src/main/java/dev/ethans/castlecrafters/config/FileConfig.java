package dev.ethans.castlecrafters.config;

import dev.ethans.castlecrafters.FoodDash;
import org.bukkit.configuration.file.YamlConfiguration;

public interface FileConfig {

    FoodDash getPlugin();

    void load();

    void save();

    void reload();

    YamlConfiguration getConfig();
}
