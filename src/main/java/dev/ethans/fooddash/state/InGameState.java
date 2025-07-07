package dev.ethans.fooddash.state;

import dev.ethans.fooddash.FoodDash;
import dev.ethans.fooddash.crops.CropManager;
import dev.ethans.fooddash.event.CropPlaceListener;
import dev.ethans.fooddash.event.DepositListener;
import dev.ethans.fooddash.event.PlayerQuitListener;
import dev.ethans.fooddash.event.WaterPlaceListener;
import dev.ethans.fooddash.state.base.GameState;
import dev.ethans.fooddash.team.Team;
import dev.ethans.fooddash.wave.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class InGameState extends GameState {

    private WaveManager waveManager;
    private CropManager cropManager;

    public InGameState() {
        super(FoodDash.getInstance());
    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ZERO;
    }

    @Override
    protected void onStart() {
        this.waveManager = new WaveManager(this);
        this.cropManager = new CropManager();

        register(new DepositListener(waveManager));
        register(new PlayerQuitListener());
        register(new CropPlaceListener(cropManager));
        register(new WaterPlaceListener(waveManager));

        // Start of game announcement
        broadcast(Component.text("The game has begun!", NamedTextColor.GREEN));

        // Teleport players
        Team.DASHERS.getPlayers().forEach(player -> {
            player.sendMessage(Component.text("Taking you to your farm!", NamedTextColor.RED));
            player.teleport(((FoodDash) plugin).getMapConfig().getMap().spawnLocations().get(Team.DASHERS));
        });

        // Start timer
        waveManager.startTimer();
    }

    @Override
    public void onUpdate() {
        if (!waveManager.getCurrentWave().items().isEmpty()) return;

        getPlayers().forEach(player ->
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1));
        broadcastTitle(Component.text("Food Delivered!", NamedTextColor.GREEN),
                Component.text("Good job! Starting the next wave!", NamedTextColor.AQUA));
        waveManager.stopTimer();
        waveManager.nextWave();
        waveManager.startTimer();
    }

    @Override
    protected void onEnd() {
        waveManager.getWaveTimer().removeAllPlayers();
    }

    @Override
    public boolean isReadyToEnd() {
        return waveManager.timeIsUp() && !waveManager.getCurrentWave().items().isEmpty();
    }
}
