package dev.ethans.castlecrafters.wave;

import dev.ethans.castlecrafters.FoodDash;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.exception.NoPacketAdapterAvailableException;
import net.megavex.scoreboardlibrary.api.noop.NoopScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import net.megavex.scoreboardlibrary.api.sidebar.component.ComponentSidebarLayout;
import net.megavex.scoreboardlibrary.api.sidebar.component.SidebarComponent;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class WaveScoreboard {

    private static final FoodDash plugin = FoodDash.getInstance();

    private final WaveManager waveManager;
    private final Sidebar scoreboard;
    private final ComponentSidebarLayout scoreboardLayout;

    private boolean enabled = true;

    public WaveScoreboard(WaveManager waveManager) {
        this.waveManager = waveManager;

        ScoreboardLibrary scoreboardLibrary;
        try {
            scoreboardLibrary = ScoreboardLibrary.loadScoreboardLibrary(plugin);
        } catch (NoPacketAdapterAvailableException e) {
            scoreboardLibrary = new NoopScoreboardLibrary();
            plugin.getLogger().warning("No scoreboard packet adapter available!");
        }

        this.scoreboard = scoreboardLibrary.createSidebar();

        SidebarComponent title = SidebarComponent.staticLine(Component.text("Food Dash", NamedTextColor.BLUE));
        SidebarComponent.Builder linesBuilder = SidebarComponent.builder()
                .addBlankLine()
                .addDynamicLine(() -> Component.text("Wave: " + waveManager.getCurrentWave().waveNumber(), NamedTextColor.GREEN))
                .addBlankLine();

        for (Material material : waveManager.getCurrentWave().items().keySet())
            linesBuilder.addDynamicLine(() -> Component.text(material.name() + ": "
                    + waveManager.getCurrentWave().items().get(material), NamedTextColor.AQUA));

        SidebarComponent lines = linesBuilder.build();
        this.scoreboardLayout = new ComponentSidebarLayout(title, lines);

        plugin.getServer().getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (!enabled) cancel();
                scoreboardLayout.apply(scoreboard);
            }
        }, 0, 1);
    }

    public Sidebar getScoreboard() {
        return scoreboard;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
