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
        return ((CastleCrafters) plugin).getGeneralConfig().getGameDuration();
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

        redTeam.getPlayers().forEach(player -> {
            player.sendMessage(Component.text("Teleporting to your team's spawn...", NamedTextColor.RED));
            player.teleport(((CastleCrafters) plugin).getMapConfig().getMap().spawnLocations().get(redTeam));
        });

        blueTeam.getPlayers().forEach(player -> {
            player.sendMessage(Component.text("Teleporting to your team's spawn...", NamedTextColor.BLUE));
            player.teleport(((CastleCrafters) plugin).getMapConfig().getMap().spawnLocations().get(blueTeam));
        });

        broadcast(Component.text("Please see your hotbar for colony management items!", NamedTextColor.GREEN));

        plugin.getServer().getLogger().info("The has started! Teams are as follows:");
        plugin.getServer().getLogger().info("Red Team: " + redTeam.getPlayers().size() + " players");
        plugin.getServer().getLogger().info("Blue Team: " + blueTeam.getPlayers().size() + " players");
    }

    @Override
    public void onUpdate() {

    }

    @Override
    protected void onEnd() {

    }
}
