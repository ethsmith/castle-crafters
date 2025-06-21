package dev.ethans.castlecrafters.state;

import dev.ethans.castlecrafters.CastleCrafters;
import dev.ethans.castlecrafters.state.base.GameState;
import dev.ethans.castlecrafters.team.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class InGameState extends GameState {

    private Team redTeam = Team.RED;
    private Team blueTeam = Team.BLUE;

    public InGameState() {
        super(CastleCrafters.getInstance());
    }

    @Override
    public @NotNull Duration getDuration() {
        return Duration.ofSeconds(plugin.getConfig().getInt("Game.Duration"));
    }

    @Override
    protected void onStart() {
        // Start of game announcement
        broadcast(Component.text("The game has begun!", NamedTextColor.GREEN));

        // Split players into teams
        getPlayers().forEach(player -> {
            Team teamToJoin = redTeam.hasMorePlayersThan(blueTeam) ? blueTeam: redTeam;
            teamToJoin.addPlayer(player);
        });

        // TODO Spawn players at their respective team positions
    }

    @Override
    public void onUpdate() {

    }

    @Override
    protected void onEnd() {

    }
}
