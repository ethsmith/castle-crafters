package dev.ethans.fooddash;

import com.maximde.hologramlib.HologramLib;
import com.maximde.hologramlib.hologram.HologramManager;
import dev.ethans.fooddash.config.GeneralConfig;
import dev.ethans.fooddash.config.MapConfig;
import dev.ethans.fooddash.state.InGameState;
import dev.ethans.fooddash.state.WaitingState;
import dev.ethans.fooddash.state.base.ScheduledStateSeries;
import org.bukkit.plugin.java.JavaPlugin;

public final class FoodDash extends JavaPlugin {

    private static FoodDash instance;

    private GeneralConfig generalConfig;
    private MapConfig mapConfig;
    private HologramManager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        if (HologramLib.getManager().isEmpty()) {
            getLogger().severe("HologramLib is unavailable, disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }

        manager = HologramLib.getManager().get();

        generalConfig = new GeneralConfig();
        generalConfig.load();

        mapConfig = new MapConfig();
        mapConfig.load();

        ScheduledStateSeries stateMachine = new ScheduledStateSeries(this);
        stateMachine.add(new WaitingState());
        stateMachine.add(new InGameState());
        stateMachine.start();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FoodDash getInstance() {
        return instance;
    }

    public GeneralConfig getGeneralConfig() {
        return generalConfig;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }

    public HologramManager getHologramManager() {
        return manager;
    }
}
