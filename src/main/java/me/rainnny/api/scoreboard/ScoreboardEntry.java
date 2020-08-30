package me.rainnny.api.scoreboard;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * @author Braydon
 */
public class ScoreboardEntry {
    @Getter private final ScoreboardProvider provider;
    @Setter private String text;
    private final String identifier;
    private Team team;

    /**
     * Creates a new scoreboard line with the provided text for the
     * provided scoreboard provider
     * @param provider - The scoreboard provider you would like to make the line for
     * @param text - The text you would like to be displayed for this line
     */
    public ScoreboardEntry(ScoreboardProvider provider, String text) {
        this.provider = provider;
        this.text = text;
        this.identifier = provider.getTeamName();
        createTeam();
    }

    /**
     * Create the team for the line
     */
    protected void createTeam() {
        Scoreboard scoreboard = provider.getScoreboard();
        if (scoreboard == null)
            return;
        String teamName = identifier;
        if (teamName.length() > 16)
            teamName = teamName.substring(0, 16);
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        if (!team.getEntries().contains(identifier)) {
            team.addEntry(identifier);
        }
        if (!provider.getEntries().contains(this))
            provider.getEntries().add(this);
        this.team = team;
    }

    /**
     * Display the line at the provided position
     * @param position - The position you would like to display the line at
     */
    protected void display(int position) {
        if (text.length() > 16) {
            String prefix = text.substring(0, 16);
            String suffix;

            if (prefix.charAt(15) == ChatColor.COLOR_CHAR) {
                prefix = prefix.substring(0, 15);
                suffix = text.substring(15);
            } else if (prefix.charAt(14) == ChatColor.COLOR_CHAR) {
                prefix = prefix.substring(0, 14);
                suffix = text.substring(14);
            } else {
                suffix = (ChatColor.getLastColors(prefix).equalsIgnoreCase(ChatColor.getLastColors(identifier)) ? "" : ChatColor.getLastColors(prefix)) + text.substring(16);
            }
            if (suffix.length() > 16)
                suffix = suffix.substring(0, 16);
            team.setPrefix(prefix);
            team.setSuffix(suffix);
        } else {
            team.setPrefix(text);
            team.setSuffix("");
        }
        Score score = provider.getObjective().getScore(identifier);
        score.setScore(position);
    }

    /**
     * Remove the line
     */
    protected void remove() {
        provider.getIdentifiers().remove(identifier);
        provider.getScoreboard().resetScores(identifier);
    }
}