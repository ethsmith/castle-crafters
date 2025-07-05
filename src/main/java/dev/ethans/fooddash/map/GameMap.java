package dev.ethans.fooddash.map;

import dev.ethans.fooddash.team.Team;
import org.bukkit.Location;

import java.util.Map;

public record GameMap(String name, Map<Team, Location> spawnLocations) {
    public GameMap {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Game map name cannot be null or empty.");
        }
        if (spawnLocations == null || spawnLocations.isEmpty()) {
            throw new IllegalArgumentException("Spawn locations cannot be null or empty.");
        }
    }
}
