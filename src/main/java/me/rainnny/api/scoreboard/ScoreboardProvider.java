package me.rainnny.api.scoreboard;

import lombok.Getter;
import me.rainnny.api.util.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Bukkit.getScoreboardManager;

/**
 * @author Braydon
 */
@Getter
public abstract class ScoreboardProvider {
    private static final Random random = new Random();

    protected final Player player;
    private final List<String> identifiers = new ArrayList<>();
    private final List<ScoreboardEntry> entries = new ArrayList<>();

    private Scoreboard scoreboard;
    private Objective objective;

    public ScoreboardProvider(Player player) {
        this.player = player;
        give();
    }

    public abstract String getTitle();
    public abstract List<String> getLines();

    /**
     * Get an entry at the provided index
     * @param index - The index of the entry you would like to get
     * @return the entry at the provided index, null if none
     */
    protected ScoreboardEntry getEntryAtPosition(int index) {
        if (index < 0 || index >= entries.size())
            return null;
        return entries.get(index);
    }

    /**
     * Get a unique name for a team
     * @return the unique name for a team
     */
    public String getTeamName() {
        ChatColor randomColor = ChatColor.values()[NumberUtils.getRandom(ChatColor.values().length - 1)];
        String identifier = randomColor.toString() + ChatColor.WHITE;
        while (identifiers.contains(identifier)) {
            identifier = identifier + randomColor + ChatColor.WHITE;
        }
        if (identifier.length() > 16) {
            return getTeamName();
        }
        identifiers.add(identifier);
        return identifier;
    }

    /**
     * Give the player the scoreboard
     */
    private void give() {
        if (player.getScoreboard().equals(getScoreboardManager().getMainScoreboard()))
            scoreboard = getScoreboardManager().getNewScoreboard();
        else
            scoreboard = player.getScoreboard();
        if (scoreboard.getObjective("Board") == null)
            objective = scoreboard.registerNewObjective("Board", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(getTitle());

        player.setScoreboard(scoreboard);
    }
}