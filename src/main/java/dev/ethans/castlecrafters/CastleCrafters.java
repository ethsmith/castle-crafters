package dev.ethans.castlecrafters;

import dev.ethans.castlecrafters.config.GeneralConfig;
import dev.ethans.castlecrafters.config.MapConfig;
import dev.ethans.castlecrafters.state.InGameState;
import dev.ethans.castlecrafters.state.WaitingState;
import dev.ethans.castlecrafters.state.base.ScheduledStateSeries;
import org.bukkit.plugin.java.JavaPlugin;

public final class CastleCrafters extends JavaPlugin {

    private static CastleCrafters instance;
    private GeneralConfig generalConfig;
    private MapConfig mapConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        generalConfig = new GeneralConfig();
        generalConfig.load();

        mapConfig = new MapConfig();
        mapConfig.load();

        ScheduledStateSeries gameStateSeries = new ScheduledStateSeries(this);
        gameStateSeries.add(new WaitingState());
        gameStateSeries.add(new InGameState());
        gameStateSeries.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CastleCrafters getInstance() {
        return instance;
    }

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }
}
