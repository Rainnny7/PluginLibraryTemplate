package me.rainnny.api.util;

import me.rainnny.api.network.ProtocolHandler;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * @author Braydon
 */
public class PlayerUtils {
    /**
     * This will reset the player
     * @param player - The player you would like to reset
     * @param gamemode - The gamemode you would like the player to set to
     * @param effects - Whether or not to remove the player's potion effects
     * @param inventory - Whether or not to clear the player's inventory
     */
    public static void reset(Player player, GameMode gamemode, boolean effects, boolean inventory) {
        player.setMaxHealth(20.0D);
        player.setHealth(player.getMaxHealth());
        player.setSaturation(3f);
        player.setExhaustion(0f);
        player.setFoodLevel(20);
        player.setExp(0f);
        player.setLevel(0);
        player.setFireTicks(0);
        player.setFallDistance(0.0f);
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
        player.setGameMode(gamemode);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setSprinting(false);
        if (effects) {
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
        }
        if (inventory) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.updateInventory();
        }
    }

    /**
     * Send the provided player an action bar with the provided text
     * @param player - The player you would like to send the actionbar to
     * @param text - The text you would like your actionbar to have
     */
    public static void displayActionbar(Player player, String text) {
        ProtocolHandler.sendPacket(player, new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}"), (byte) 2));
    }
}
