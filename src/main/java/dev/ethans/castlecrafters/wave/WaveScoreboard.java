package dev.ethans.castlecrafters.wave;

import dev.ethans.castlecrafters.FoodDash;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveScoreboard {

    private static final FoodDash plugin = FoodDash.getInstance();
    private final Map<Player, FastBoard> boards = new HashMap<>();
    private final WaveManager waveManager;

    public WaveScoreboard(WaveManager waveManager) {
        this.waveManager = waveManager;
        waveManager.getGameState().getPlayers().forEach(player -> {
            FastBoard board = new FastBoard(player);
            board.updateTitle(ChatColor.BLUE +"Food Dash");
            boards.put(player, board);
        });
    }

    public void update() {
        boards.forEach((player, board) -> {
            List<String> lines = new ArrayList<>();
            lines.add("");
            lines.add(ChatColor.GREEN + "Wave: " + waveManager.getCurrentWave().waveNumber());
            lines.add("");

            for (ItemType type : waveManager.getCurrentWave().items().keySet()) {
                lines.add(ChatColor.AQUA + type.asMaterial().name().replace("_", " ")
                        + ": " + waveManager.getCurrentWave().items().get(type));
            }
        });
    }
}
