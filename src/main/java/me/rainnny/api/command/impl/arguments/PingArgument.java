package me.rainnny.api.command.impl.arguments;

import me.rainnny.api.command.Command;
import me.rainnny.api.command.CommandException;
import me.rainnny.api.command.CommandProvider;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 */
public class PingArgument {
    @Command(name = "ping", usage = "<player>", description = "Pingggggg", playersOnly = true)
    public void onCommand(CommandProvider command) throws CommandException {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        Player target = player;
        if (args.length > 1)
            target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            throw new CommandException("hi, player is not online!");
        }
        player.sendMessage("§7" + (player.equals(target) ? "Your" : "§e" + target.getName() + "'s") + " ping is §a" + ((CraftPlayer) target).getHandle().ping + "ms");
    }
}