package dev.ethans.castlecrafters.config;

import dev.ethans.castlecrafters.CastleCrafters;
import org.bukkit.configuration.file.YamlConfiguration;

public interface FileConfig {

    CastleCrafters getPlugin();

    void load();

    void save();

    void reload();

    YamlConfiguration getConfig();
}
