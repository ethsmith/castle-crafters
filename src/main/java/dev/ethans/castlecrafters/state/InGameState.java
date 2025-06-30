package dev.ethans.castlecrafters.state;

import dev.ethans.castlecrafters.FoodDash;
import dev.ethans.castlecrafters.event.DepositListener;
import dev.ethans.castlecrafters.state.base.GameState;
import dev.ethans.castlecrafters.team.Team;
import dev.ethans.castlecrafters.wave.WaveManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class InGameState extends GameState {

    private WaveManager waveManager;

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
        register(new DepositListener(waveManager));

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

        broadcast(Component.text("You delivered all the food, starting the next wave!", NamedTextColor.GREEN));
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
