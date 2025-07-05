package dev.ethans.fooddash.config;

import dev.ethans.fooddash.FoodDash;
import org.bukkit.configuration.file.YamlConfiguration;

public interface FileConfig {

    FoodDash getPlugin();

    void load();

    void save();

    void reload();

    YamlConfiguration getConfig();
}
