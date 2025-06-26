package dev.ethans.castlecrafters.config;

import dev.ethans.castlecrafters.FoodDash;
import dev.ethans.castlecrafters.map.GameMap;
import dev.ethans.castlecrafters.team.Team;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

public class MapConfig implements FileConfig {

    private GameMap map;
    private YamlConfiguration config;

    @Override
    public FoodDash getPlugin() {
        return FoodDash.getInstance();
    }

    @Override
    public void load() {
        String mapPath = "maps/" + getPlugin().getGeneralConfig().getMap() + ".yml";
        getPlugin().getLogger().info("Loading Map: " + mapPath);
        File mapFile = getPlugin().getDataFolder().toPath().resolve(mapPath).toFile();
        getPlugin().getLogger().info("Map: " + mapFile.getName());

        getPlugin().saveResource(mapPath, false);
        config = YamlConfiguration.loadConfiguration(mapFile);

        String mapName = config.getString("World");
        if (mapName == null || mapName.isEmpty())
            throw new IllegalArgumentException("Map name cannot be null or empty in the configuration.");

        Map<Team, Location> spawnLocations = config.getConfigurationSection("Spawns").getValues(false)
                .entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Team.valueOf(entry.getKey()),
                        entry -> new Location(getPlugin().getServer().createWorld(new WorldCreator(mapName)), config.getDouble("Spawns." + entry.getKey() + ".X"),
                                config.getDouble("Spawns." + entry.getKey() + ".Y"),
                                config.getDouble("Spawns." + entry.getKey() + ".Z")
                )));
        this.map = new GameMap(mapName, spawnLocations);
    }

    @Override
    public void save() {
        // Not needed currently
    }

    @Override
    public void reload() {
        load();
    }

    @Override
    public YamlConfiguration getConfig() {
        return config;
    }

    public GameMap getMap() {
        return map;
    }
}
