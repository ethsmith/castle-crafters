package dev.ethans.castlecrafters.team;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public enum Team {

    RED(Component.text("Red", NamedTextColor.RED, TextDecoration.BOLD)),
    BLUE(Component.text("Blue", NamedTextColor.BLUE, TextDecoration.BOLD));

    private final TextComponent displayName;
    private final List<Player> players = new ArrayList<>();

    Team(TextComponent displayName) {
        this.displayName = displayName;
    }

    public TextComponent getDisplayName() {
        return displayName;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.contains(player)) return;
        players.add(player);
        player.sendMessage(Component.text("You have joined the ", NamedTextColor.GREEN)
                .append(displayName)
                .append(Component.text(" team.", NamedTextColor.GREEN)));
    }

    public void removePlayer(Player player) {
        if (!players.contains(player)) return;
        players.remove(player);
        player.sendMessage(Component.text("You have left the ", NamedTextColor.RED)
                .append(displayName)
                .append(Component.text(" team.", NamedTextColor.RED)));
    }

    public boolean hasMorePlayersThan(Team other) {
        return this.players.size() > other.players.size();
    }
}
