package me.rainnny.api.scoreboard;

import lombok.Getter;
import me.rainnny.api.util.Style;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getScoreboardManager;

/**
 * @author Braydon
 */
@Getter
public class ScoreboardHandler implements Listener {
    @Getter private static ScoreboardHandler instance;

    private final JavaPlugin plugin;

    private final Class<? extends ScoreboardProvider> boardClass;
    private Thread thread;
    private final long delay;

    private final HashMap<Player, ScoreboardProvider> boards = new HashMap<>();

    public ScoreboardHandler(JavaPlugin plugin, Class<? extends ScoreboardProvider> boardClass, long delay) {
        instance = this;
        this.plugin = plugin;
        this.boardClass = boardClass;
        this.delay = delay;

        for (Player player : Bukkit.getOnlinePlayers())
            giveBoard(player);
        (thread = new Thread("Scoreboard Thread") {
            @Override
            public void run() {
                while (true) {
                    try {
                        tick();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        sleep(delay * 50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            private void tick() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ScoreboardProvider provider = boards.get(player);
                    if (provider == null)
                        continue;
                    Objective objective = provider.getObjective();
                    String title = provider.getTitle();

                    if (!objective.getDisplayName().equals(title))
                        objective.setDisplayName(title);

                    List<String> newLines = provider.getLines();

                    if (newLines == null || newLines.isEmpty()) {
                        provider.getEntries().forEach(ScoreboardEntry::remove);
                        provider.getEntries().clear();
                    } else {
                        Collections.reverse(newLines);

                        if (provider.getEntries().size() > newLines.size()) {
                            for (int i = newLines.size(); i < provider.getEntries().size(); i++) {
                                ScoreboardEntry entry = provider.getEntryAtPosition(i);
                                if (entry != null)
                                    entry.remove();
                            }
                        }
                        int index = 1;
                        for (int i = 0; i < newLines.size(); i++) {
                            ScoreboardEntry entry = provider.getEntryAtPosition(i);
                            String line = Style.color(newLines.get(i));
                            if (entry == null)
                                entry = new ScoreboardEntry(provider, line);
                            entry.setText(line);
                            entry.createTeam();
                            entry.display(index++);
                        }
                    }
                }
            }
        }).start();

        getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When a player joins the server, we wanna give them
     * the scoreboard
     */
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        giveBoard(event.getPlayer());
    }

    /**
     * When a player leaves the server, we wanna remove the
     * scoreboard from them
     */
    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        removeBoard(event.getPlayer());
    }

    /**
     * When the plugin is disabled, we wanna stop the
     * scoreboard thread and remove the scoreboard from
     * all online players
     */
    @EventHandler
    private void onDisable(PluginDisableEvent event) {
        if (plugin.equals(event.getPlugin())) {
            if (thread != null) {
                thread.stop();
                thread = null;
            }
            for (Player player : Bukkit.getOnlinePlayers())
                removeBoard(player);
        }
    }

    /**
     * Give the provided player the scoreboard
     * @param player - The player you would like to give the scoreboard to
     */
    public void giveBoard(Player player) {
        if (boards.containsKey(player))
            throw new IllegalStateException("Player '" + player.getName() + "' already has the scoreboard");
        try {
            boards.put(player, (ScoreboardProvider) boardClass.getConstructors()[0].newInstance(player));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Remove the scoreboard from the provided player
     * @param player - The player you would like to remove the scoreboard from
     */
    public void removeBoard(Player player) {
        if (!boards.containsKey(player))
            throw new IllegalStateException("Player '" + player.getName() + "' does not have the scoreboard");
        player.setScoreboard(getScoreboardManager().getMainScoreboard());
        boards.remove(player);
    }
}