package me.rainnny.api.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 * This object is constructed when a player executes a command.
 * It holds information such as the sender, label, and args
 */
@AllArgsConstructor @Getter
public class CommandProvider {
    private final CommandSender sender;
    private final String label;
    private final String[] args;

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public Player getPlayer() {
        if (!isPlayer())
            return null;
        return (Player) sender;
    }
}